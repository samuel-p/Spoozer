package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 15.11.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbumResponse {
    private String id;
    private String name;
    private List<SpotifyImageResponse> images;

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

    public List<SpotifyImageResponse> getImages() {
        return images;
    }

    public void setImages(List<SpotifyImageResponse> images) {
        this.images = images;
    }
}