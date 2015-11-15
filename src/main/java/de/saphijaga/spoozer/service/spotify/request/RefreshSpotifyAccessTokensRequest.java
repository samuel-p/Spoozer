package de.saphijaga.spoozer.service.spotify.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by samuel on 15.11.15.
 */
public class RefreshSpotifyAccessTokensRequest {
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("grant_type")
    private String grantType = "refresh_token";

    public RefreshSpotifyAccessTokensRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getGrantType() {
        return grantType;
    }
}
