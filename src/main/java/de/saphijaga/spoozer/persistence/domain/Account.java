package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by samuel on 27.10.15.
 */
public class Account {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}