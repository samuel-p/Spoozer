package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.TrackDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by samuel on 08.11.15.
 */
public class GetServiceTrackResultResponse {
    private Map<String, List<TrackDetails>> tracks;

    public GetServiceTrackResultResponse(Map<String, List<TrackDetails>> tracks) {
        this.tracks = tracks;
    }

    public Map<String, List<TrackDetails>> getTracks() {
        return tracks;
    }

    public void setTracks(Map<String, List<TrackDetails>> tracks) {
        this.tracks = tracks;
    }
}