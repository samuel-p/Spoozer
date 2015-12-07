package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.service.StreamingService;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xce35l5 on 06.12.2015.
 */
public class TrackRequest {
    @NotNull
    @NotEmpty
    private String playlistId;

    @NotEmpty
    @NotNull
    private String trackId;

    @NotNull
    private StreamingService service;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public StreamingService getService() {
        return service;
    }

    public void setService(StreamingService service) {
        this.service = service;
    }
}
