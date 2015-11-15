package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;

import java.util.Optional;

/**
 * Created by samuel on 14.11.15.
 */
public interface AccountAccessService {
    <T extends AccountAccessDetails> Optional<T> saveAccessDetails(AccountDetails accountDetails, AccountAccessDetails accessDetails);

    <T extends AccountAccessDetails> Optional<T> getAccessDetails(UserDetails user, StreamingService service);
}