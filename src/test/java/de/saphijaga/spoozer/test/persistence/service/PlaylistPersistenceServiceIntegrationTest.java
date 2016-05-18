package de.saphijaga.spoozer.test.persistence.service;

import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.service.PlaylistPersistenceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;



import static de.saphijaga.spoozer.test.data.TestPlaylistFactory.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jega on 18.05.16.
 */
public class PlaylistPersistenceServiceIntegrationTest extends MongoIntegrationTest {
    @Autowired
    private PlaylistPersistenceService playlistService;
    
    @Test
    public void shouldReturnNullForEmptyId() throws Exception {
        assertThat(playlistService.getPlaylist(""), is(empty()));
    }

    @Test
    public void shouldSavePlaylistAndAddID() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setName("testList");
        Optional<Playlist> created = playlistService.savePlaylist(playlist);
        assertTrue(created.isPresent());
        assertThat(created.get(),is(playlist));
    }

    @Test
    public void shouldDeletePlaylist() throws Exception {
        Playlist playlist = findPlaylist(TEST_ID);
        playlistService.deletePlaylist(playlist);
        assertNull(findPlaylist(TEST_ID));
    }
}
