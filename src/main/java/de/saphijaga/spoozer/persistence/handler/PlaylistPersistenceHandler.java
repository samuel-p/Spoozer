package de.saphijaga.spoozer.persistence.handler;

import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.repository.PlaylistRepository;
import de.saphijaga.spoozer.persistence.service.PlaylistPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 * Created by xce35l5 on 04.12.2015.
 */
@Component
public class PlaylistPersistenceHandler implements PlaylistPersistenceService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public Optional<Playlist> savePlaylist(Playlist playlist) {
        if(playlist.getId()==null){
            playlist.setId(UUID.randomUUID().toString());
        }
        Playlist savedList = playlistRepository.save(playlist);
        return ofNullable(savedList);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        playlistRepository.delete(playlist);
    }

    @Override
    public Optional<Playlist> getPlaylist(String id) {
        return ofNullable(playlistRepository.findOne(id));
    }
}
