package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by samuel on 31.03.16.
 */
@Document(collection = "account")
public class SoundcloudAccount extends Account {
    private String displayname;
    private String accessToken;
    private String refreshToken;

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
}
