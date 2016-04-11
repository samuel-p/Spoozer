package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyRecommendationsResponse {
    private List<SpotifyTrackResponse> tracks;

    public List<SpotifyTrackResponse> getTracks() {
        return tracks;
    }

    public void setTracks(List<SpotifyTrackResponse> tracks) {
        this.tracks = tracks;
    }
}