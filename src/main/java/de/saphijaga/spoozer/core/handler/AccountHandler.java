package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.persistence.domain.SoundcloudAccount;
import de.saphijaga.spoozer.persistence.domain.SpotifyAccount;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.AccountPersistenceService;
import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.soundcloud.Soundcloud;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.SoundcloudAccountDetails;
import de.saphijaga.spoozer.web.details.SpotifyAccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

/**
 * Created by samuel on 13.11.15.
 */
@Component
public class AccountHandler implements AccountService {
    // TODO outsource api parsing to api implementations
    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private AccountPersistenceService accountService;

    @Override
    public <T extends AccountDetails> Optional<T> saveAccount(UserDetails userDetails, T accountDetails) {
        Optional<Account> account = accountService.saveAccount(detailsToAccount(accountDetails));
        if (accountDetails.getId() == null && account.isPresent()) {
            Optional<User> user = userService.getUser(userDetails.getId());
            if (user.isPresent()) {
                user.get().getAccounts().add(account.get());
                userService.saveUser(user.get());
            }
        }
        return ofNullable(accountToDetails(account.orElse(null)));
    }

    private <T extends AccountDetails> T accountToDetails(Account account) {
        if (account == null) {
            return null;
        }
        if (account instanceof SpotifyAccount) {
            SpotifyAccountDetails details = new SpotifyAccountDetails();
            details.setId(account.getId());
            details.setUsername(account.getUsername());
            details.setUrl(account.getUrl());
            details.setDisplayname(((SpotifyAccount) account).getDisplayname());
            return (T) details;
        }
        if (account instanceof SoundcloudAccount) {
            SoundcloudAccountDetails details = new SoundcloudAccountDetails();
            details.setId(account.getId());
            details.setUsername(account.getUsername());
            details.setUrl(account.getUrl());
            details.setDisplayname(((SoundcloudAccount) account).getDisplayname());
            return (T) details;
        }
        throw new IllegalArgumentException("StreamingAccount not supported yed");
    }

    private Account detailsToAccount(AccountDetails details) {
        if (details instanceof SpotifyAccountDetails) {
            SpotifyAccount account = new SpotifyAccount();
            account.setId(details.getId());
            account.setUsername(details.getUsername());
            account.setUrl(details.getUrl());
            account.setDisplayname(((SpotifyAccountDetails) details).getDisplayname());
            return account;
        }
        if (details instanceof SoundcloudAccountDetails) {
            SoundcloudAccount account = new SoundcloudAccount();
            account.setId(details.getId());
            account.setUsername(details.getUsername());
            account.setUrl(details.getUrl());
            account.setDisplayname(((SoundcloudAccountDetails) details).getDisplayname());
            return account;
        }
        throw new IllegalArgumentException("StreamingAccount not supported yed");
    }

    @Override
    public void deleteAccount(UserDetails userDetails, AccountDetails accountDetails) {
        Account account = detailsToAccount(accountDetails);
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            user.get().getAccounts().removeIf(a -> a.getId().equals(account.getId()));
            userService.saveUser(user.get());
        }
        accountService.deleteAccount(account);
    }

    @Override
    public <T extends AccountDetails> Optional<T> getAccount(UserDetails userDetails, StreamingService streamingService) {
        List<AccountDetails> accountDetails = getAccountList(userDetails);
        return (Optional<T>) accountDetails.stream().filter(account -> account.getClass().equals(streamingService.getAccountDetailsClass())).findAny();
    }



    @Override
    public List<AccountDetails> getAccountList(UserDetails userDetails) {
        List<Account> accounts = userService.getUser(userDetails.getId()).map(User::getAccounts).orElse(asList());
        List<AccountDetails> accountDetails = new ArrayList<>();
        accounts.forEach(account -> accountDetails.add(accountToDetails(account)));
        return accountDetails;
    }
}