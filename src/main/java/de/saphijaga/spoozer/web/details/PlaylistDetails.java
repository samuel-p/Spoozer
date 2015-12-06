package de.saphijaga.spoozer.web.details;

/**
 * Created by samuel on 27.10.15.
 */
public class PlaylistDetails {
    private String id;
    private String name;
    private int trackCount;

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

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }
}