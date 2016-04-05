package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyNewReleasesResponse {
    private SpotifyNewReleasesResponse albums;

    public SpotifyNewReleasesResponse getAlbums() {
        return albums;
    }

    public void setAlbums(SpotifyNewReleasesResponse albums) {
        this.albums = albums;
    }
}