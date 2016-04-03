package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.saphijaga.spoozer.service.spotify.Spotify;

import java.util.List;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylistsResponse {
    private List<SpotifyPlaylistResponse> items;

    public List<SpotifyPlaylistResponse> getItems() {
        return items;
    }

    public void setItems(List<SpotifyPlaylistResponse> items) {
        this.items = items;
    }
}
