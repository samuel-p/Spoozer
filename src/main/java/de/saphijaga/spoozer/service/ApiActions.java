package de.saphijaga.spoozer.service;

/**
 * Created by samuel on 13.11.15.
 */
public enum ApiActions {
    PROFILE("/me"), SEARCH_TRACKS("/search?type=track&market=from_token&q="), GET_TRACK("/tracks/");

    private String spotify;

    ApiActions(String spotify) {
        this.spotify = spotify;
    }

    public String getSpotify() {
        return spotify;
    }
}