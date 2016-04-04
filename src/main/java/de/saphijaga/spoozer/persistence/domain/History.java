package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * Created by jan-ericgaidusch on 31.03.16.
 */
@Document
public class History {

    @Id
    private String id;
    private List<HTrack> songs;

    public History() {
        songs = new ArrayList<>();
    }

    public void addSong(Track track) {
        songs.add(new HTrack(UUID.randomUUID().toString(), track, new Date()));
    }

    public void cutListtoLimit(int limit) {
        if (limit > -1)
            while (songs.size() > limit)
                songs.remove(0);
    }

    public List<HTrack> getSongs(){
        return songs;
    }

    public void clearHistory(){
        songs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
