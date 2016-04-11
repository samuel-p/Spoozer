package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyFeaturedPlaylistsResponse {
    private SpotifyPlaylistsResponse playlists;

    public SpotifyPlaylistsResponse getPlaylists() {
        return playlists;
    }

    public void setPlaylists(SpotifyPlaylistsResponse playlists) {
        this.playlists = playlists;
    }
}