package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyPlaylistTracksResponse {
    private List<SpotifyPlaylistTrackResponse> items;

    public List<SpotifyPlaylistTrackResponse> getItems() {
        return items;
    }

    public void setItems(List<SpotifyPlaylistTrackResponse> items) {
        this.items = items;
    }
}