package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by jan-ericgaidusch on 31.03.16.
 */
@Document
public class HTrack {
    @Id
    private String id;

    @DBRef
    private Track track;

    private Date date;

    public HTrack(){
        // used by Spring
    }

    public HTrack(String id,Track track, Date date) {
        this.id = id;
        this.track = track;
        this.date = date;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HTrack hTrack = (HTrack) o;

        if (id != null ? !id.equals(hTrack.id) : hTrack.id != null) return false;
        if (track != null ? !track.equals(hTrack.track) : hTrack.track != null) return false;
        return date != null ? date.equals(hTrack.date) : hTrack.date == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (track != null ? track.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
