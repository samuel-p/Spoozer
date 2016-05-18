package de.saphijaga.spoozer.test.data;

import de.saphijaga.spoozer.persistence.domain.Playlist;

/**
 * Created by jega on 18.05.16.
 */
public class TestPlaylistFactory {
    public static final String TEST_ID = "bf2a2b0a-adca-4458-bf62-9464548736bd";
    public static final String TEST_NAME = "Testlist";

    public static Playlist testPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setName(TEST_NAME);
        playlist.setId(TEST_ID);
        return playlist;
    }
}
