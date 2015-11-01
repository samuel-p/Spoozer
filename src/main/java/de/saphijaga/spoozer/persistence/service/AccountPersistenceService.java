package de.saphijaga.spoozer.persistence.service;

import de.saphijaga.spoozer.persistence.domain.Account;

import java.util.Optional;

/**
 * Created by samuel on 27.10.15.
 */
public interface AccountPersistenceService {
    Optional<Account> saveAccount(Account account);

    void deleteAccount(Account account);
}
