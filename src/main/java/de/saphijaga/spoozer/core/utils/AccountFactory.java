package de.saphijaga.spoozer.core.utils;

import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.domain.SoundcloudAccount;
import de.saphijaga.spoozer.persistence.domain.SpotifyAccount;
import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 08.05.16.
 */
public class AccountFactory {
    public static Account create(StreamingService service) {
        switch (service) {
            case SPOTIFY:
                return new SpotifyAccount();
            case SOUNDCLOUD:
                return new SoundcloudAccount();
            default:
                throw new IllegalArgumentException("No Account available for given StreamingService!");
        }
    }

    private AccountFactory() {
    }
}
