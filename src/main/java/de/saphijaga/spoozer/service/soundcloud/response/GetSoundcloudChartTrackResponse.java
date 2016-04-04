package de.saphijaga.spoozer.service.soundcloud.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSoundcloudChartTrackResponse {
    private double score;
    private GetSoundcloudTrackResponse track;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public GetSoundcloudTrackResponse getTrack() {
        return track;
    }

    public void setTrack(GetSoundcloudTrackResponse track) {
        this.track = track;
    }
}