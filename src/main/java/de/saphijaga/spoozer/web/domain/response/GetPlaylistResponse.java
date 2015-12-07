package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.TracklistDetails;

/**
 * Created by samuel on 07.12.15.
 */
public class GetPlaylistResponse {
    private TracklistDetails playlist;

    public GetPlaylistResponse(TracklistDetails playlist) {
        this.playlist = playlist;
    }

    public TracklistDetails getPlaylist() {
        return playlist;
    }
}