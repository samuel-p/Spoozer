package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.AccountAccessService;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.AccountPersistenceService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static de.saphijaga.spoozer.service.StreamingService.valueOfAccountClass;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

/**
 * Created by samuel on 14.11.15.
 */
@Component
public class AccountAccessHandler implements AccountAccessService {
    @Autowired
    private AccountPersistenceService accountService;

    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private ApiService api;

    @Override
    public <T extends AccountAccessDetails> Optional<T> getAccessDetails(UserDetails userDetails, StreamingService service) {
        Optional<User> user = userService.getUser(userDetails.getId());
        List<Account> accounts = user.map(User::getAccounts).orElse(emptyList());
        Optional<Account> account = accounts.stream().filter(a -> a.getClass().equals(service.getAccountClass())).findAny();
        return ofNullable(accountToAccessDetails(account.orElse(null)));
    }

    private <T extends AccountAccessDetails> T accountToAccessDetails(Account account) {
        if (account == null) {
            return null;
        }
        return (T) api.getApi(valueOfAccountClass(account.getClass())).getAccountAccessDetailsFromAccount(account);
    }

    @Override
    public <T extends AccountAccessDetails> Optional<T> saveAccessDetails(AccountDetails accountDetails, AccountAccessDetails accessDetails) {
        Optional<Account> account = accountService.getAccount(accountDetails.getId());
        if (account.isPresent()) {
            Account a = updateAccountWithAccessDetails(account.get(), accessDetails);
            account = accountService.saveAccount(a);
        }
        return ofNullable(accountToAccessDetails(account.orElse(null)));
    }

    private Account updateAccountWithAccessDetails(Account account, AccountAccessDetails accessDetails) {
        api.getApi(valueOfAccountClass(account.getClass())).updateAccount(account, accessDetails);
        return account;
    }
}
