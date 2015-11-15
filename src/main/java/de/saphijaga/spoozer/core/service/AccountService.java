package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * Created by samuel on 13.11.15.
 */
public interface AccountService {
    <T extends AccountDetails> Optional<T> saveAccount(UserDetails user, T account);

    void deleteAccount(UserDetails user, AccountDetails account);

    <T extends AccountDetails> Optional<T> getAccount(UserDetails user, StreamingService streamingService);

    List<AccountDetails> getAccountList(UserDetails user);
}