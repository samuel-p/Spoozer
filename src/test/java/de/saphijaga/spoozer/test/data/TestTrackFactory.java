package de.saphijaga.spoozer.test.data;

import de.saphijaga.spoozer.persistence.domain.History;
import de.saphijaga.spoozer.persistence.domain.Track;

/**
 * Created by jega on 19.05.16.
 */
public class TestTrackFactory {
    public static String TEST_ID = "bf2a2b0a-adca-4444-bf62-9464548736ce";

    public static Track testTrackWithoutID(){
        Track track = new Track();
        return track;
    }

    public static Track testTrack() {
        Track track = testTrackWithoutID();
        track.setId(TEST_ID);
        return track;
    }
}
