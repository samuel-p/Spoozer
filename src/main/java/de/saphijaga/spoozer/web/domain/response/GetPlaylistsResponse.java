package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.PlaylistDetails;

import java.util.List;

/**
 * Created by xce35l5 on 05.12.2015.
 */
public class GetPlaylistsResponse {
    private List<PlaylistDetails> playlists;

    public GetPlaylistsResponse(List<PlaylistDetails> playlists) {
        this.playlists = playlists;
    }

    public List<PlaylistDetails> getPlaylists() {
        return playlists;
    }
}
