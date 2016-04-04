package de.saphijaga.spoozer.service;

import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.domain.SoundcloudAccount;
import de.saphijaga.spoozer.persistence.domain.SpotifyAccount;
import de.saphijaga.spoozer.service.soundcloud.Soundcloud;
import de.saphijaga.spoozer.service.soundcloud.SoundcloudApi;
import de.saphijaga.spoozer.service.spotify.SpotifyApi;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.SoundcloudAccountDetails;
import de.saphijaga.spoozer.web.details.SpotifyAccountDetails;

import java.util.Arrays;

import static java.util.Arrays.stream;

/**
 * Created by samuel on 13.11.15.
 */
public enum StreamingService {
    SPOTIFY("Spotify", SpotifyAccount.class, SpotifyAccountDetails.class, SpotifyApi.class),
    SOUNDCLOUD("Soundcloud", SoundcloudAccount.class, SoundcloudAccountDetails.class, SoundcloudApi.class);

    private String name;
    private Class<? extends Account> accountClass;
    private Class<? extends AccountDetails> accountDetailsClass;
    private Class<? extends Api> apiClass;

    StreamingService(String name, Class<? extends Account> accountClass, Class<? extends AccountDetails> accountDetailsClass, Class<? extends Api> apiClass) {
        this.name = name;
        this.accountClass = accountClass;
        this.accountDetailsClass = accountDetailsClass;
        this.apiClass = apiClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Account> getAccountClass() {
        return accountClass;
    }

    public Class<? extends AccountDetails> getAccountDetailsClass() {
        return accountDetailsClass;
    }

    public Class<? extends Api> getApiClass() {
        return apiClass;
    }

    public static StreamingService valueOfAccountClass(Class<? extends Account> accountClass) {
        return stream(values()).filter(value -> value.getAccountClass().equals(accountClass)).findAny().orElse(null);
    }

    public static StreamingService valueOfAccountDetailsClass(Class<? extends AccountDetails> accountDetailsClass) {
        return stream(values()).filter(value -> value.getAccountDetailsClass().equals(accountDetailsClass)).findAny().orElse(null);
    }
}