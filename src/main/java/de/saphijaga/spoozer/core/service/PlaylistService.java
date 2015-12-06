package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.web.details.PlaylistDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddPlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.SongRequest;

import java.util.List;
import java.util.Optional;

/**
 * Created by xce35l5 on 04.12.2015.
 */
public interface PlaylistService {

    void addPlaylist(UserDetails user, AddPlaylistRequest request);

    <T extends PlaylistDetails> Optional<T> savePlaylist(UserDetails user, PlaylistDetails playlist);

    void addSongToPlaylist(UserDetails user, SongRequest request);

    void deletePlaylist(UserDetails user, PlaylistDetails playlist);

    Playlist getPlaylist(UserDetails user, PlaylistDetails playlist);

    List<PlaylistDetails> getPlaylists(UserDetails user);

    void removeSongFromPlaylist(UserDetails user, SongRequest request);
}
