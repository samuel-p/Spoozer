package de.saphijaga.spoozer.service.soundcloud;

import de.saphijaga.spoozer.persistence.domain.SoundcloudAccount;
import de.saphijaga.spoozer.service.AccessDetailsExpiredException;
import de.saphijaga.spoozer.service.BaseApi;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.soundcloud.request.GetSoundcloudAccessTokensRequest;
import de.saphijaga.spoozer.service.soundcloud.request.RefreshSoundcloudAccessTokensRequest;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudAccessTokensResponse;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudChartTracksResponse;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudProfileResponse;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudTrackResponse;
import de.saphijaga.spoozer.service.utils.Get;
import de.saphijaga.spoozer.service.utils.Post;
import de.saphijaga.spoozer.web.details.SoundcloudAccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.util.UriUtils.encode;

/**
 * Created by samuel on 31.03.16.
 */
@Component
public class SoundcloudApi extends BaseApi<SoundcloudAccount, SoundcloudAccountDetails, SoundcloudAccessDetails> {
    private static Logger logger = LoggerFactory.getLogger(SoundcloudApi.class);

    @Override
    public StreamingService getService() {
        return StreamingService.SOUNDCLOUD;
    }

    @Override
    public SoundcloudAccountDetails getAccountDetailsFromAccount(SoundcloudAccount account) {
        SoundcloudAccountDetails details = new SoundcloudAccountDetails();
        details.setId(account.getId());
        details.setUsername(account.getUsername());
        details.setUrl(account.getUrl());
        details.setDisplayname(account.getDisplayname());
        return details;
    }

    @Override
    public SoundcloudAccessDetails getAccountAccessDetailsFromAccount(SoundcloudAccount account) {
        SoundcloudAccessDetails accessDetails = new SoundcloudAccessDetails();
        accessDetails.setAccessToken(account.getAccessToken());
        accessDetails.setRefreshToken(account.getRefreshToken());
        return accessDetails;
    }

    @Override
    public void updateAccount(SoundcloudAccount account, SoundcloudAccountDetails details) {
        account.setUsername(details.getUsername());
        account.setUrl(details.getUrl());
        account.setDisplayname(details.getDisplayname());

    }

    @Override
    public void updateAccount(SoundcloudAccount account, SoundcloudAccessDetails accessDetails) {
        account.setAccessToken(accessDetails.getAccessToken());
        account.setRefreshToken(accessDetails.getRefreshToken());
    }

    public String getLoginURL(String serverUrl, String state) throws UnsupportedEncodingException {
        return Soundcloud.LOGIN_URL + "?client_id=" + Soundcloud.CLIENT_ID + "&redirect_uri=" + encode(getRedirectUrl(serverUrl), "utf8")
                + "&scope=" + encode(Soundcloud.SCOPE, "utf8") + "&state=" + state + "&response_type=code";
    }

    private String getRedirectUrl(String serverUrl) {
        return serverUrl + Soundcloud.REDIRECT_URL_PATH;
    }

    private String getApiUrl(String action) {
        return Soundcloud.API_URL + action;
    }

    private String getApiUrl2(String action) {
        return Soundcloud.API_URL_2 + action;
    }

    public SoundcloudAccountDetails login(UserDetails user, String code, String serverUrl) throws IOException {
        GetSoundcloudAccessTokensRequest request = new GetSoundcloudAccessTokensRequest(code, getRedirectUrl(serverUrl));
        GetSoundcloudAccessTokensResponse accessTokenResponse = Post.forObject(Soundcloud.TOKEN_URL, request, GetSoundcloudAccessTokensResponse.class);
        SoundcloudAccessDetails accessDetails = new SoundcloudAccessDetails();
        accessDetails.setAccessToken(accessTokenResponse.getAccessToken());
        accessDetails.setRefreshToken(accessTokenResponse.getRefreshToken());
        Optional<SoundcloudAccountDetails> result = accountService.getAccount(user, StreamingService.SOUNDCLOUD);
        SoundcloudAccountDetails accountDetails = result.orElse(new SoundcloudAccountDetails());
        requestAccountDetails(accessDetails, accountDetails);
        Optional<SoundcloudAccountDetails> soundcloudAccountDetails = accountService.saveAccount(user, accountDetails);
        if (soundcloudAccountDetails.isPresent()) {
            accessService.saveAccessDetails(soundcloudAccountDetails.get(), accessDetails);
        }
        return soundcloudAccountDetails.orElse(null);
    }

    private void requestAccountDetails(SoundcloudAccessDetails accessDetails, SoundcloudAccountDetails accountDetails) {
        try {
            GetSoundcloudProfileResponse profileResponse = Get.forObject(getApiUrl("/me") + "?oauth_token=" + accessDetails.getAccessToken(), GetSoundcloudProfileResponse.class);
            accountDetails.setUsername(profileResponse.getUsername());
            accountDetails.setDisplayname(profileResponse.getFullName());
            accountDetails.setUrl(profileResponse.getPermalinkUrl());
        } catch (IOException e) {
            logger.warn("Unable to request account details", e);
        }
    }

    @Override
    public SoundcloudAccountDetails updateAccountDetails(UserDetails user) {
        Optional<SoundcloudAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SOUNDCLOUD);
        Optional<SoundcloudAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SOUNDCLOUD);
        if (accountDetails.isPresent() && accessDetails.isPresent()) {
            requestAccountDetails(accessDetails.get(), accountDetails.get());
            return accountService.saveAccount(user, accountDetails.get()).get();
        }
        return accountDetails.get();
    }

    @Override
    protected void refreshAccessDetails(UserDetails user, SoundcloudAccessDetails accessDetails) {
        RefreshSoundcloudAccessTokensRequest request = new RefreshSoundcloudAccessTokensRequest(accessDetails.getRefreshToken());
        try {
            GetSoundcloudAccessTokensResponse accessTokenResponse = Post.forObject(Soundcloud.TOKEN_URL, request, GetSoundcloudAccessTokensResponse.class);
            accessDetails.setAccessToken(accessTokenResponse.getAccessToken());
            accessDetails.setRefreshToken(accessTokenResponse.getRefreshToken());
            Optional<SoundcloudAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SOUNDCLOUD);
            if (accountDetails.isPresent()) {
                accessService.saveAccessDetails(accountDetails.get(), accessDetails);
            }
        } catch (IOException e) {
            logger.warn("Unable to refresh access details!", e);
        }
    }

    @Override
    protected List<TrackDetails> getSearchResult(SoundcloudAccessDetails accessDetails, String search) throws AccessDetailsExpiredException {
        try {
            String url = getApiUrl("/tracks") + "?oauth_token=" + accessDetails.getAccessToken() + "&q=" + encode(search.trim(), "utf8") + "&filter=streamable&limit=20";
            GetSoundcloudTrackResponse[] searchResponse = Get.forObject(url, GetSoundcloudTrackResponse[].class);
            List<TrackDetails> tracks = new ArrayList<>();
            stream(searchResponse).forEach(track -> tracks.add(trackToDetails(track)));
            return tracks;
        } catch (IOException e) {
            return throwOrReturn(e, emptyList());
        }
    }

    @Override
    protected TrackDetails getTrack(SoundcloudAccessDetails accessDetails, String id) throws AccessDetailsExpiredException {
        try {
            String url = getApiUrl("/tracks/") + id + "?oauth_token=" + accessDetails.getAccessToken();
            GetSoundcloudTrackResponse response = Get.forObject(url, GetSoundcloudTrackResponse.class);
            return trackToDetails(response);
        } catch (IOException e) {
            return throwOrReturn(e, null);
        }
    }

    @Override
    protected List<TrackDetails> getChartTracks(SoundcloudAccessDetails accessDetails) throws AccessDetailsExpiredException {
        try {
            String url = getApiUrl2("/charts") + "?oauth_token=" + accessDetails.getAccessToken() + "&kind=top&limit=10";
            GetSoundcloudChartTracksResponse response = Get.forObject(url, GetSoundcloudChartTracksResponse.class);
            return response.getCollection().stream().map(chart -> trackToDetails(chart.getTrack())).collect(toList());
        } catch (IOException e) {
            return throwOrReturn(e, emptyList());
        }
    }

    @Override
    protected List<TrackDetails> getNewReleasedTracks(SoundcloudAccessDetails accessDetails) throws AccessDetailsExpiredException {
        try {
            String url = getApiUrl("/tracks") + "?oauth_token=" + accessDetails.getAccessToken() + "&filter=streamable&limit=10&order=created_at";
            GetSoundcloudTrackResponse[] searchResponse = Get.forObject(url, GetSoundcloudTrackResponse[].class);
            return stream(searchResponse).map(this::trackToDetails).collect(toList());
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

    private TrackDetails trackToDetails(GetSoundcloudTrackResponse track) {
        TrackDetails trackDetails = new TrackDetails();
        trackDetails.setService(StreamingService.SOUNDCLOUD);
        trackDetails.setId(track.getId());
        trackDetails.setTitle(track.getTitle());
        trackDetails.setDurationInMillis(track.getDurationInMillis());
        trackDetails.setInterpret(track.getLabelName());
        trackDetails.setAlbum(track.getUser().getFullName());
        trackDetails.setCoverUrl((StringUtils.isNotEmpty(track.getArtworkUrl())) ? track.getArtworkUrl() : track.getUser().getAvatarUrl());
        trackDetails.setUrl(track.getStreamUrl());
        trackDetails.setExternalUrl(track.getUri());
        return trackDetails;
    }
}