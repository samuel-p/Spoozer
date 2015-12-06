package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.persistence.domain.Track;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xce35l5 on 06.12.2015.
 */
public class SongRequest {
    @NotNull
    @NotEmpty
    private String playListName;

    @NotEmpty
    @NotNull
    private Track track;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }
}
