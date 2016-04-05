package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylistTrackResponse {
    private SpotifyTrackResponse track;

    public SpotifyTrackResponse getTrack() {
        return track;
    }

    public void setTrack(SpotifyTrackResponse track) {
        this.track = track;
    }
}