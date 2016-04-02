package de.saphijaga.spoozer.web.details;

import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 31.03.16.
 */
public class SoundcloudAccountDetails extends AccountDetails {
    private String displayname;

    public SoundcloudAccountDetails() {
        super(StreamingService.SOUNDCLOUD);
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
}
