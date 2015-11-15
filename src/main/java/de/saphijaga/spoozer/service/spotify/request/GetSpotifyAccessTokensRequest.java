package de.saphijaga.spoozer.service.spotify.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by samuel on 13.11.15.
 */
public class GetSpotifyAccessTokensRequest {
    private String code;
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";
    @JsonProperty("redirect_uri")
    private String redirectUri;

    public GetSpotifyAccessTokensRequest(String code, String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public String getCode() {
        return code;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}