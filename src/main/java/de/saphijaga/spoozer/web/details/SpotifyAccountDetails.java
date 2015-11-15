package de.saphijaga.spoozer.web.details;

import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 13.11.15.
 */
public class SpotifyAccountDetails extends AccountDetails {
    private String displayname;

    public SpotifyAccountDetails() {
        super(StreamingService.SPOTIFY);
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
}