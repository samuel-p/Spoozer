package de.saphijaga.spoozer.test.persistence.service;

import com.mongodb.Mongo;
import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.persistence.service.TrackPersistenceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


import static de.saphijaga.spoozer.test.data.TestTrackFactory.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jega on 19.05.16.
 */
public class TrackPersistenceServiceIntegrationTest extends MongoIntegrationTest {
    @Autowired
    TrackPersistenceService trackService;

    @Test
    public void shouldReturnNullForEmptyId() throws Exception {
        assertThat(trackService.getTrack(""), is(empty()));
    }

    @Test
    public void shouldReturnTrackByID() throws Exception {
        assertThat(trackService.getTrack(TEST_ID),is(of(testTrack())));
    }

    @Test
    public void shouldSaveTrackAndAddID() throws Exception {
        Track track = new Track();
        Optional<Track> created = trackService.saveTrack(track);
        assertTrue(created.isPresent());
        assertThat(created.get(),is(track));
    }

    @Test
    public void shouldDeleteTrack() throws Exception {
        Track track = findTrack(TEST_ID);
        trackService.deleteTrack(track);
        assertNull(findPlaylist(TEST_ID));
    }
}
