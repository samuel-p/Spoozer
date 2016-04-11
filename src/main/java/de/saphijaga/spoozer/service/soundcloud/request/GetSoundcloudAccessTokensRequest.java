package de.saphijaga.spoozer.service.soundcloud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.saphijaga.spoozer.service.soundcloud.Soundcloud;

/**
 * Created by samuel on 31.03.16.
 */
public class GetSoundcloudAccessTokensRequest {
    private String code;
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("client_id")
    private String clientId = Soundcloud.CLIENT_ID;
    @JsonProperty("client_secret")
    private String clientSecret = Soundcloud.CLIENT_SECRET;

    public GetSoundcloudAccessTokensRequest(String code, String redirectUri) {
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

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
