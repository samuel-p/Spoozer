package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.AccountAccessService;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.domain.SpotifyAccount;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.AccountPersistenceService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.spotify.SpotifyAccessDetails;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by samuel on 14.11.15.
 */
@Component
public class AccountAccessHandler implements AccountAccessService {
    @Autowired
    private AccountPersistenceService accountService;

    @Autowired
    private UserPersistenceService userService;

    @Override
    public <T extends AccountAccessDetails> Optional<T> getAccessDetails(UserDetails userDetails, StreamingService service) {
        Optional<User> user = userService.getUser(userDetails.getId());
        List<Account> accounts = user.map(User::getAccounts).orElse(emptyList());
        Optional<Account> account = accounts.stream().filter(a -> a.getClass().equals(service.getAccountClass())).findAny();
        return accountToAccessDetails(account);
    }

    private <T extends AccountAccessDetails> Optional<T> accountToAccessDetails(Optional<Account> account) {
        if (!account.isPresent()) {
            return empty();
        }
        if (account.get() instanceof SpotifyAccount) {
            SpotifyAccessDetails accessDetails = new SpotifyAccessDetails();
            accessDetails.setAccessToken(((SpotifyAccount) account.get()).getAccessToken());
            accessDetails.setRefreshToken(((SpotifyAccount) account.get()).getRefreshToken());
            accessDetails.setTokenType(((SpotifyAccount) account.get()).getTokenType());
            return of((T) accessDetails);
        }
        return empty();
    }

    @Override
    public <T extends AccountAccessDetails> Optional<T> saveAccessDetails(AccountDetails accountDetails, AccountAccessDetails accessDetails) {
        Optional<Account> account = accountService.getAccount(accountDetails.getId());
        if (account.isPresent()) {
            Account a = updateAccountWithAccessDetails(account.get(), accessDetails);
            account = accountService.saveAccount(a);
        }
        return accountToAccessDetails(account);
    }

    private Account updateAccountWithAccessDetails(Account account, AccountAccessDetails accessDetails) {
        if (account instanceof SpotifyAccount && accessDetails instanceof SpotifyAccessDetails) {
            SpotifyAccount a = ((SpotifyAccount) account);
            a.setAccessToken(accessDetails.getAccessToken());
            a.setRefreshToken(((SpotifyAccessDetails) accessDetails).getRefreshToken());
            a.setTokenType(((SpotifyAccessDetails) accessDetails).getTokenType());
            return a;
        }
        return account;
    }
}
