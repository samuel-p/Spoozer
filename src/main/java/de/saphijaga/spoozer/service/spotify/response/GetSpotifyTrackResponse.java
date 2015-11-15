package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 15.11.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyTrackResponse {
    private List<SpotifyTrackResponse> items;

    public List<SpotifyTrackResponse> getItems() {
        return items;
    }

    public void setItems(List<SpotifyTrackResponse> items) {
        this.items = items;
    }
}