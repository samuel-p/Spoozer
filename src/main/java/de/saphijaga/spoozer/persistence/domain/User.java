package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.List;

/**
 * Created by samuel on 14.10.15.
 */
public class User {
    @Id
    private String id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private List<Playlist> playlists;
    private List<Account> accounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}