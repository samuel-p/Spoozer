package de.saphijaga.spoozer.service;

/**
 * Created by samuel on 14.11.15.
 */
public abstract class AccountAccessDetails {
    private StreamingService service;
    private String accessToken;

    public AccountAccessDetails(StreamingService service) {
        this.service = service;
    }

    public StreamingService getService() {
        return service;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}