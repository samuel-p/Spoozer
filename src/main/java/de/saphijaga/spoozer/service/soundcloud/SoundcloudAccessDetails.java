package de.saphijaga.spoozer.service.soundcloud;

import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 31.03.16.
 */
public class SoundcloudAccessDetails extends AccountAccessDetails {
    private String refreshToken;

    public SoundcloudAccessDetails() {
        super(StreamingService.SPOTIFY);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
