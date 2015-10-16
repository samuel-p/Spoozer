package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

/**
 * Created by samuel on 14.10.15.
 */
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private String id;
    private String username;
    private String password;
    private String email;

    public User() {
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}