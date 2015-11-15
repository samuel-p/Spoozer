package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by samuel on 15.11.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyArtistResponse {
    private String id;
    private String name;

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
}
