package de.saphijaga.spoozer.persistence.handler;

import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.repository.AccountRepository;
import de.saphijaga.spoozer.persistence.service.AccountPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 * Created by samuel on 13.11.15.
 */
@Component
public class AccountPersistenceHandler implements AccountPersistenceService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> getAccount(String id) {
        return ofNullable(accountRepository.findOne(id));
    }

    @Override
    public Optional<Account> saveAccount(Account account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID().toString());
        }
        Account savedAccount = accountRepository.save(account);
        return ofNullable(savedAccount);
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account.getId());
    }
}