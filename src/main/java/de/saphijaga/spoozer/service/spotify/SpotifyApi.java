package de.saphijaga.spoozer.service.spotify;

import de.saphijaga.spoozer.core.service.AccountAccessService;
import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.ApiActions;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.spotify.request.RefreshSpotifyAccessTokensRequest;
import de.saphijaga.spoozer.service.spotify.response.*;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.service.utils.Get;
import de.saphijaga.spoozer.service.utils.Post;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.SpotifyAccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.service.spotify.request.GetSpotifyAccessTokensRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static de.saphijaga.spoozer.service.spotify.Spotify.*;
import static java.lang.String.join;
import static java.util.Collections.emptyList;
import static org.springframework.util.Base64Utils.encodeToString;
import static org.springframework.web.util.UriUtils.encode;

/**
 * Created by samuel on 14.11.15.
 */
@Component
public class SpotifyApi implements Api {
    @Autowired
    private ApiService service;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountAccessService accessService;

    @PostConstruct
    public void init() {
        service.registerApi(this);
    }

    public String getLoginURL(String serverUrl, String state) throws UnsupportedEncodingException {
        return LOGIN_URL + "?client_id=" + CLIENT_ID + "&redirect_uri=" + encode(getRedirectUrl(serverUrl), "utf8")
                + "&scope=" + encode(join(" ", SCOPES), "utf8") + "&state=" + state + "&response_type=code";
    }

    public String getRedirectUrl(String serverUrl) {
        return serverUrl + REDIRECT_URL_PATH;
    }

    public SpotifyAccountDetails login(UserDetails user, String code, String serverUrl) throws IOException {
        GetSpotifyAccessTokensRequest request = new GetSpotifyAccessTokensRequest(code, getRedirectUrl(serverUrl));
        Map<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", getServerAuthorizationKey());
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
    public AccountDetails updateAccountDetails(UserDetails user) {
        Optional<SpotifyAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SPOTIFY);
        Optional<SpotifyAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SPOTIFY);
        if (accountDetails.isPresent() && accessDetails.isPresent()) {
            try {
                requestAccountDetails(accessDetails.get(), accountDetails.get());
                return accountService.saveAccount(user, accountDetails.get()).get();
            } catch (IOException e) {
            }
        }
        return accountDetails.get();
    }

    private void requestAccountDetails(SpotifyAccessDetails accessDetails, SpotifyAccountDetails accountDetails) throws IOException {
        GetSpotifyProfileResponse profileResponse = Get.forObject(getApiUrl(ApiActions.PROFILE), getHeader(accessDetails), GetSpotifyProfileResponse.class);
        accountDetails.setUsername(profileResponse.getId());
        accountDetails.setDisplayname(profileResponse.getDisplayname());
        accountDetails.setUrl(profileResponse.getExternalUrls().get("spotify"));
    }

    private Map<String, String> getHeader(SpotifyAccessDetails accessDetails) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", accessDetails.getTokenType() + " " + accessDetails.getAccessToken());
        return header;
    }

    private String getServerAuthorizationKey() {
        return "Basic " + encodeToString((Spotify.CLIENT_ID + ":" + Spotify.CLIENT_SECRET).getBytes());
    }

    private String getApiUrl(ApiActions api) {
        return API_URL + api.getSpotify();
    }

    @Override
    public StreamingService getService() {
        return StreamingService.SPOTIFY;
    }

    @Override
    public List<TrackDetails> getSearchResult(UserDetails user, String search) {
        return getSearchResult(user, search, true);
    }

    private List<TrackDetails> getSearchResult(UserDetails user, String search, boolean retry) {
        Optional<SpotifyAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SPOTIFY);
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        String url = getApiUrl(ApiActions.SEARCH_TRACKS) + search.trim().replace(" ", "%20OR%20");
        try {
            GetSpotifyTrackSearchResponse searchResponse = Get.forObject(url, getHeader(accessDetails.get()), GetSpotifyTrackSearchResponse.class);
            List<TrackDetails> tracks = new ArrayList<>();
            searchResponse.getTracks().getItems().forEach(track -> tracks.add(trackToDetails(track)));
            return tracks;
        } catch (IOException e) {
            e.printStackTrace();
            if (e.getMessage().contains("401") && retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getSearchResult(user, search, false);
            }
        }
        return emptyList();
    }

    private void refreshAccessDetails(UserDetails user, SpotifyAccessDetails accessDetails) {
        RefreshSpotifyAccessTokensRequest request = new RefreshSpotifyAccessTokensRequest(accessDetails.getRefreshToken());
        Map<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", getServerAuthorizationKey());
        try {
            GetSpotifyAccessTokensResponse accessTokenResponse = Post.forObject(Spotify.TOKEN_URL, request, authHeader, GetSpotifyAccessTokensResponse.class);
            accessDetails.setAccessToken(accessTokenResponse.getAccessToken());
            accessDetails.setTokenType(accessTokenResponse.getTokenType());
            Optional<SpotifyAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SPOTIFY);
            if (accountDetails.isPresent()) {
                accessService.saveAccessDetails(accountDetails.get(), accessDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TrackDetails trackToDetails(SpotifyTrackResponse track) {
        TrackDetails trackDetails = new TrackDetails();
        trackDetails.setId(track.getId());
        trackDetails.setTitle(track.getName());
        trackDetails.setDurationInMillis(track.getDurationInMillis());
        List<String> artists = new ArrayList<>();
        track.getArtists().forEach(a -> artists.add(a.getName()));
        trackDetails.setInterpret(String.join(", ", artists));
        trackDetails.setAlbum(track.getAlbum().getName());
        trackDetails.setCoverUrl(track.getAlbum().getImages().stream().findAny().map(SpotifyImageResponse::getUrl).orElse(null));
        trackDetails.setUrl(track.getPreviewUrl());
        trackDetails.setExternalUrl(track.getUri());
        return trackDetails;
    }
}