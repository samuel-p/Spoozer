package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.web.details.PlaylistDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.TracklistDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddPlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.TrackRequest;

import java.util.List;
import java.util.Optional;

/**
 * Created by xce35l5 on 04.12.2015.
 */
public interface PlaylistService {
    void addPlaylist(UserDetails user, AddPlaylistRequest request);

    void addSongToPlaylist(UserDetails user, String id, TrackDetails track);

    void deletePlaylist(UserDetails user, String id);

    Optional<TracklistDetails> getPlaylist(UserDetails user, String id);

    List<PlaylistDetails> getPlaylists(UserDetails user);

    void deleteSongFromPlaylist(UserDetails user, TrackRequest request);
}
