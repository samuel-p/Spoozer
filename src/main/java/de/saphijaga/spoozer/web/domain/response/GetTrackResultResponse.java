package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.TrackDetails;

import java.util.List;

/**
 * Created by samuel on 04.04.16.
 */
public class GetTrackResultResponse {
    private List<TrackDetails> tracks;

    public GetTrackResultResponse(List<TrackDetails> tracks) {
        this.tracks = tracks;
    }

    public List<TrackDetails> getTracks() {
        return tracks;
    }
}
