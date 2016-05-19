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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TracklistDetails that = (TracklistDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return tracks != null ? tracks.equals(that.tracks) : that.tracks == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }
}