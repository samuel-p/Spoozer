package de.saphijaga.spoozer.service.soundcloud;

import de.saphijaga.spoozer.core.service.AccountAccessService;
import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.soundcloud.request.GetSoundcloudAccessTokensRequest;
import de.saphijaga.spoozer.service.soundcloud.request.RefreshSoundcloudAccessTokensRequest;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudAccessTokensResponse;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudProfileResponse;
import de.saphijaga.spoozer.service.soundcloud.response.GetSoundcloudTrackResponse;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.service.utils.Get;
import de.saphijaga.spoozer.service.utils.Post;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.SoundcloudAccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static org.springframework.web.util.UriUtils.encode;

/**
 * Created by samuel on 31.03.16.
 */
@Component
public class SoundcloudApi implements Api {
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

    @Override
    public StreamingService getService() {
        return StreamingService.SOUNDCLOUD;
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

    private void requestAccountDetails(SoundcloudAccessDetails accessDetails, SoundcloudAccountDetails accountDetails) throws IOException {
        GetSoundcloudProfileResponse profileResponse = Get.forObject(getApiUrl("/me") + "?oauth_token=" + accessDetails.getAccessToken(), GetSoundcloudProfileResponse.class);
        accountDetails.setUsername(profileResponse.getUsername());
        accountDetails.setDisplayname(profileResponse.getFullName());
        accountDetails.setUrl(profileResponse.getPermalinkUrl());
    }

    @Override
    public AccountDetails updateAccountDetails(UserDetails user) {
        Optional<SoundcloudAccountDetails> accountDetails = accountService.getAccount(user, StreamingService.SOUNDCLOUD);
        Optional<SoundcloudAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SOUNDCLOUD);
        if (accountDetails.isPresent() && accessDetails.isPresent()) {
            try {
                requestAccountDetails(accessDetails.get(), accountDetails.get());
                return accountService.saveAccount(user, accountDetails.get()).get();
            } catch (IOException e) {
            }
        }
        return accountDetails.get();
    }

    private void refreshAccessDetails(UserDetails user, SoundcloudAccessDetails accessDetails) {
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
            e.printStackTrace();
        }
    }

    @Override
    public List<TrackDetails> getSearchResult(UserDetails user, String search) {
        return getSearchResult(user, search, true);
    }

    private List<TrackDetails> getSearchResult(UserDetails user, String search, boolean retry) {
        Optional<SoundcloudAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SOUNDCLOUD);
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        try {
            System.out.println(encode(search.trim(), "utf8"));
            String url = getApiUrl("/tracks") + "?oauth_token=" + accessDetails.get().getAccessToken() + "&q=" + encode(search.trim(), "utf8") + "&limit=20";
            GetSoundcloudTrackResponse[] searchResponse = Get.forObject(url, GetSoundcloudTrackResponse[].class);
            List<TrackDetails> tracks = new ArrayList<>();
            stream(searchResponse).forEach(track -> tracks.add(trackToDetails(track)));
            return tracks;
        } catch (IOException e) {
            if (e.getMessage().contains("401") && retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getSearchResult(user, search, false);
            }
            e.printStackTrace();
        }
        return emptyList();
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

    @Override
    public TrackDetails getTrack(UserDetails user, String id) {
        return getTrack(user, id, true);
    }

    private TrackDetails getTrack(UserDetails user, String id, boolean retry) {
        Optional<SoundcloudAccessDetails> accessDetails = accessService.getAccessDetails(user, StreamingService.SOUNDCLOUD);
        if (!accessDetails.isPresent()) {
            return null;
        }
        try {
            String url = getApiUrl("/tracks/") + id + "?oauth_token=" + accessDetails.get().getAccessToken();
            GetSoundcloudTrackResponse response = Get.forObject(url, GetSoundcloudTrackResponse.class);
            return trackToDetails(response);
        } catch (IOException e) {
            if (e.getMessage().contains("401") && retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getTrack(user, id, false);
            }
            e.printStackTrace();
        }
        return null;
    }
}
