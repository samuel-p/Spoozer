package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by samuel on 27.10.15.
 */
public class Playlist {
    @Id
    private String id;
    private String name;
    private List<Track> tracks;

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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}