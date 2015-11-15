package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by samuel on 13.11.15.
 */
@Document(collection = "account")
public class SpotifyAccount extends Account {
    private String displayname;
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}