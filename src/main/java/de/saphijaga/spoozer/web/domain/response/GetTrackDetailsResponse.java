package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.TrackDetails;

/**
 * Created by samuel on 05.04.16.
 */
public class GetTrackDetailsResponse {
    private TrackDetails track;

    public GetTrackDetailsResponse(TrackDetails track) {
        this.track = track;
    }

    public TrackDetails getTrack() {
        return track;
    }
}
