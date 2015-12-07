package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.service.StreamingService;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xce35l5 on 06.12.2015.
 */
public class SongRequest {
    @NotNull
    @NotEmpty
    private String playListID;

    @NotEmpty
    @NotNull
    private String trackID;

    @NotEmpty
    @NotNull
    private String streamingService;

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public String getPlayListID() {
        return playListID;
    }

    public void setPlayListID(String playListID) {
        this.playListID = playListID;
    }

    public String getStreamingService() {
        return streamingService;
    }

    public void setStreamingService(String streamingService) {
        this.streamingService = streamingService;
    }
}
