package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 15.11.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyTrackSearchResponse {
    private GetSpotifyTrackResponse tracks;
    private int total;
    private int limit;
    private int offset;

    public GetSpotifyTrackResponse getTracks() {
        return tracks;
    }

    public void setTracks(GetSpotifyTrackResponse tracks) {
        this.tracks = tracks;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}