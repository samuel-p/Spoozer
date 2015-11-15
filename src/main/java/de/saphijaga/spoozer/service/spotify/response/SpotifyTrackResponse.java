package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by samuel on 15.11.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackResponse {
    private String id;
    private String name;
    private SpotifyAlbumResponse album;
    private List<SpotifyArtistResponse> artists;
    @JsonProperty("duration_ms")
    private long durationInMillis;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpotifyAlbumResponse getAlbum() {
        return album;
    }

    public void setAlbum(SpotifyAlbumResponse album) {
        this.album = album;
    }

    public List<SpotifyArtistResponse> getArtists() {
        return artists;
    }

    public void setArtists(List<SpotifyArtistResponse> artists) {
        this.artists = artists;
    }

    public long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }
}