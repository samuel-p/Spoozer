package de.saphijaga.spoozer.persistence.service;

import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.domain.User;

import java.util.Optional;

/**
 * Created by samuel on 27.10.15.
 */
public interface PlaylistPersistenceService {

    Optional<Playlist> savePlaylist(Playlist playlist);

    void deletePlaylist(Playlist playlist);

    Optional<Playlist> getPlaylist(String id);
}