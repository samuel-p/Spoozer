package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.web.details.PlaylistDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xce35l5 on 05.12.2015.
 */
public class GetPlaylistsResponse {
    private List<PlaylistDetails> playlists;

    public GetPlaylistsResponse(){
        playlists = new ArrayList<>();
    }

    public List<PlaylistDetails> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDetails> playlists) {
        this.playlists = playlists;
    }
}
