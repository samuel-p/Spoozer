package de.saphijaga.spoozer.web.details;

import java.util.List;

/**
 * Created by samuel on 07.12.15.
 */
public class TracklistDetails {
    private String id;
    private String name;
    private List<TrackDetails> tracks;

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

    public List<TrackDetails> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDetails> tracks) {
        this.tracks = tracks;
    }
}