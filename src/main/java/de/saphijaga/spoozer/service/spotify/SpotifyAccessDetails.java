package de.saphijaga.spoozer.service.spotify;

import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 14.11.15.
 */
public class SpotifyAccessDetails extends AccountAccessDetails {
    private String refreshToken;
    private String tokenType;

    public SpotifyAccessDetails() {
        super(StreamingService.SPOTIFY);
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