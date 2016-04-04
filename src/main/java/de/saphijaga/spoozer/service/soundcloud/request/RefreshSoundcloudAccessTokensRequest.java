package de.saphijaga.spoozer.service.soundcloud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.saphijaga.spoozer.service.soundcloud.Soundcloud;

/**
 * Created by samuel on 31.03.16.
 */
public class RefreshSoundcloudAccessTokensRequest {
    @JsonProperty("client_id")
    private String clientId = Soundcloud.CLIENT_ID;
    @JsonProperty("client_secret")
    private String clientSecret = Soundcloud.CLIENT_SECRET;
    @JsonProperty("refresh_token")
    private String refreshToken;

    public RefreshSoundcloudAccessTokensRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
