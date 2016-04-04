package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyNewReleasesResponse {
    private List<SpotifyAlbumResponse> items;

    public List<SpotifyAlbumResponse> getItems() {
        return items;
    }

    public void setItems(List<SpotifyAlbumResponse> items) {
        this.items = items;
    }
}