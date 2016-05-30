package de.saphijaga.spoozer.service.spotify;

import de.saphijaga.spoozer.persistence.domain.SpotifyAccount;
import de.saphijaga.spoozer.service.AccessDetailsExpiredException;
import de.saphijaga.spoozer.service.BaseApi;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.spotify.request.GetSpotifyAccessTokensRequest;
import de.saphijaga.spoozer.service.spotify.request.RefreshSpotifyAccessTokensRequest;
import de.saphijaga.spoozer.service.spotify.response.*;
import de.saphijaga.spoozer.service.utils.Get;
import de.saphijaga.spoozer.service.utils.Post;
import de.saphijaga.spoozer.web.details.SpotifyAccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.lang.String.join;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Base64Utils.encodeToString;
import static org.springframework.web.util.UriUtils.encode;

/**
 * Created by samuel on 14.11.15.
 */
@Component
public class SpotifyApi extends BaseApi<SpotifyAccount, SpotifyAccountDetails, SpotifyAccessDetails> {
    public static final String AUTHORIZATION = "Authorization";
    private static Logger logger = LoggerFactory.getLogger(SpotifyApi.class);

    @Override
    public StreamingService getService() {
        return StreamingService.SPOTIFY;
    }

    @Override
    public SpotifyAccountDetails getAccountDetailsFromAccount(SpotifyAccount account) {
        SpotifyAccountDetails details = new SpotifyAccountDetails();
        details.setId(account.getId());
        details.setUsername(account.getUsername());
        details.setUrl(account.getUrl());
        details.setDisplayname(account.getDisplayname());
        return details;
    }

    @Override
    public SpotifyAccessDetails getAccountAccessDetailsFromAccount(SpotifyAccount account) {
        SpotifyAccessDetails accessDetails = new SpotifyAccessDetails();
        accessDetails.setAccessToken(account.getAccessToken());
        accessDetails.setRefreshToken(account.getRefreshToken());
        accessDetails.setTokenType(account.getTokenType());
        return accessDetails;
    }

    @Override
    public void updateAccount(SpotifyAccount account, SpotifyAccountDetails details) {
        account.setUsername(details.getUsername());
        account.setUrl(details.getUrl());
        account.setDisplayname(details.getDisplayname());
    }

    @Override
    public void updateAccount(SpotifyAccount account, SpotifyAccessDetails accessDetails) {
        account.setAccessToken(accessDetails.getAccessToken());
        account.setRefreshToken(accessDetails.getRefreshToken());
        account.setTokenType(accessDetails.getTokenType());
    }

    public String getLoginURL(String serverUrl, String state) throws UnsupportedEncodingException {
        return Spotify.LOGIN_URL + "?client_id=" + Spotify.CLIENT_ID + "&redirect_uri=" + encode(getRedirectUrl(serverUrl), "utf8")
                + "&scope=" + encode(join(" ", Spotify.getSCOPES()), "utf8") + "&state=" + state + "&response_type=code";
    }

    private String getRedirectUrl(String serverUrl) {
        return serverUrl + Spotify.REDIRECT_URL_PATH;
    }

    public SpotifyAccountDetails login(UserDetails user, String code, String serverUrl) throws IOException {
        GetSpotifyAccessTokensRequest request = new GetSpotifyAccessTokensRequest(code, getRedirectUrl(serverUrl));
        Map<String, String> authHeader = new HashMap<>();
        authHeader.put(AUTHORIZATION, getServerAuthorizationKey());
        GetSpotifyAccessTokensResponse accessTokenResponse = Post.forObject(Spotify.TOKEN_URL, request, authHeader, GetSpotifyAccessTokensResponse.class);
        SpotifyAccessDetails accessDetails = new SpotifyAccessDetails();
        accessDetails.setAccessToken(accessTokenResponse.getAccessToken());
        accessDetails.setRefreshToken(accessTokenResponse.getRefreshToken());
        accessDetails.setTokenType(accessTokenResponse.getTokenType());
        Optional<SpotifyAccountDetails> result = accountService.getAccount(user, StreamingService.SPOTIFY);
        SpotifyAccountDetails accountDetails = result.orElse(new SpotifyAccountDetails());
        requestAccountDetails(accessDetails, accountDetails);
        Optional<SpotifyAccountDetails> spotifyAccountDetails = accountService.saveAccount(user, accountDetails);
        if (spotifyAccountDetails.isPresent()) {
            accessService.saveAccessDetails(spotifyAccountDetails.get(), accessDetails);
        }
        return spotifyAccountDetails.orElse(null);
    }

    @Override
    public SpotifyAccountDetails updateAccountDetails(UserDetails user) {
        Optional<SpotifyAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SPOTIFY);
        Optional<SpotifyAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SPOTIFY);
        if (accountDetails.isPresent() && accessDetails.isPresent()) {
            requestAccountDetails(accessDetails.get(), accountDetails.get());
            return accountService.saveAccount(user, accountDetails.get()).get();
        }
        return accountDetails.get();
    }

    private Map<String, String> getHeader(SpotifyAccessDetails accessDetails) {
        Map<String, String> header = new HashMap<>();
        header.put(AUTHORIZATION, accessDetails.getTokenType() + " " + accessDetails.getAccessToken());
        return header;
    }

    private void requestAccountDetails(SpotifyAccessDetails accessDetails, SpotifyAccountDetails accountDetails) {
        try {
            GetSpotifyProfileResponse profileResponse = Get.forObject(getApiUrl("/me"), getHeader(accessDetails), GetSpotifyProfileResponse.class);
            accountDetails.setUsername(profileResponse.getId());
            accountDetails.setDisplayname(profileResponse.getDisplayname());
            accountDetails.setUrl(profileResponse.getExternalUrls().get("spotify"));
        } catch (IOException e) {
            logger.warn("Unable to request account details!", e);
        }
    }

    @Override
    protected void refreshAccessDetails(UserDetails user, SpotifyAccessDetails accessDetails) {
        RefreshSpotifyAccessTokensRequest request = new RefreshSpotifyAccessTokensRequest(accessDetails.getRefreshToken());
        Map<String, String> authHeader = new HashMap<>();
        authHeader.put(AUTHORIZATION, getServerAuthorizationKey());
        try {
            GetSpotifyAccessTokensResponse accessTokenResponse = Post.forObject(Spotify.TOKEN_URL, request, authHeader, GetSpotifyAccessTokensResponse.class);
            accessDetails.setAccessToken(accessTokenResponse.getAccessToken());
            accessDetails.setTokenType(accessTokenResponse.getTokenType());
            Optional<SpotifyAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SPOTIFY);
            accountDetails.ifPresent(details -> accessService.saveAccessDetails(accountDetails.get(), accessDetails));
        } catch (IOException e) {
            logger.warn("Unable to refresh access details!", e);
        }
    }

    private String getServerAuthorizationKey() {
        return "Basic " + encodeToString((Spotify.CLIENT_ID + ":" + Spotify.CLIENT_SECRET).getBytes());
    }

    private String getApiUrl(String action) {
        return Spotify.API_URL + action;
    }

    @Override
    protected List<TrackDetails> getSearchResult(SpotifyAccessDetails accessDetails, String search, int limit) throws AccessDetailsExpiredException {
        String url = getApiUrl("/search?type=track&market=from_token&q=") + search.trim().replace(" ", "%20OR%20") + "&limit=" + limit;
        try {
            GetSpotifyTrackSearchResponse searchResponse = Get.forObject(url, getHeader(accessDetails), GetSpotifyTrackSearchResponse.class);
            return searchResponse.getTracks().getItems().stream().map(this::trackToDetails).collect(toList());
        } catch (IOException e) {
            return throwOrReturn(e, emptyList());
        }
    }

    @Override
    protected TrackDetails getTrack(SpotifyAccessDetails accessDetails, String id) throws AccessDetailsExpiredException {
        String url = getApiUrl("/tracks/") + id;
        try {
            SpotifyTrackResponse trackResponse = Get.forObject(url, getHeader(accessDetails), SpotifyTrackResponse.class);
            return trackToDetails(trackResponse);
        } catch (IOException e) {
            return throwOrReturn(e, null);
        }
    }

    @Override
    protected List<TrackDetails> getNewReleasedTracks(SpotifyAccessDetails accessDetails) throws AccessDetailsExpiredException {
        try {
            GetSpotifyNewReleasesResponse response = Get.forObject(getApiUrl("/browse/new-releases"), getHeader(accessDetails), GetSpotifyNewReleasesResponse.class);
            List<SpotifyAlbumResponse> newReleases = response.getAlbums().getItems().stream().filter(album -> "album".equals(album.getAlbumType())).collect(toList());
            SpotifyAlbumResponse randomAlbum = newReleases.get(new Random().nextInt(newReleases.size()));
            GetSpotifyTrackResponse trackResponse = Get.forObject(getApiUrl("/albums/" + randomAlbum.getId() + "/tracks"), getHeader(accessDetails), GetSpotifyTrackResponse.class);
            trackResponse.getItems().forEach(track -> track.setAlbum(randomAlbum));
            return trackResponse.getItems().stream().map(this::trackToDetails).collect(toList());
        } catch (IOException e) {
            return throwOrReturn(e, emptyList());
        }
    }

    @Override
    protected List<TrackDetails> getFeaturedTracks(SpotifyAccessDetails accessDetails) throws AccessDetailsExpiredException {
        try {
            GetSpotifyFeaturedPlaylistsResponse response = Get.forObject(getApiUrl("/browse/featured-playlists"), getHeader(accessDetails), GetSpotifyFeaturedPlaylistsResponse.class);
            SpotifyPlaylistResponse randomPlaylist = response.getPlaylists().getItems().get(new Random().nextInt(response.getPlaylists().getItems().size()));
            GetSpotifyPlaylistTracksResponse trackResponse = Get.forObject(randomPlaylist.getTracks().getHref(), getHeader(accessDetails), GetSpotifyPlaylistTracksResponse.class);
            return trackResponse.getItems().stream().map(track -> trackToDetails(track.getTrack())).collect(toList());
        } catch (IOException e) {
            return throwOrReturn(e, emptyList());
        }
    }

    private <T> T throwOrReturn(Exception e, T returnObject) throws AccessDetailsExpiredException {
        if (e.getMessage().contains("401")) {
            throw new AccessDetailsExpiredException(e);
        }
        return returnObject;
    }

    private TrackDetails trackToDetails(SpotifyTrackResponse track) {
        TrackDetails trackDetails = new TrackDetails();
        trackDetails.setService(StreamingService.SPOTIFY);
        trackDetails.setId(track.getId());
        trackDetails.setTitle(track.getName());
        trackDetails.setDurationInMillis(track.getDurationInMillis());
        trackDetails.setInterpret(join(", ", track.getArtists().stream().map(SpotifyArtistResponse::getName).collect(toList())));
        trackDetails.setAlbum(track.getAlbum().getName());
        trackDetails.setCoverUrl(track.getAlbum().getImages().stream().findAny().map(SpotifyImageResponse::getUrl).orElse(null));
        trackDetails.setUrl(track.getPreviewUrl());
        trackDetails.setExternalUrl(track.getUri());
        return trackDetails;
    }
}