package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.core.utils.AccountFactory;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.AccountPersistenceService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.saphijaga.spoozer.core.utils.AccountFactory.create;
import static de.saphijaga.spoozer.service.StreamingService.valueOfAccountClass;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

/**
 * Created by samuel on 13.11.15.
 */
@Component
public class AccountHandler implements AccountService {
    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private AccountPersistenceService accountService;

    @Autowired
    private ApiService api;

    @Override
    public <T extends AccountDetails> Optional<T> saveAccount(UserDetails userDetails, T accountDetails) {
        Account account = getOrCreateAccount(accountDetails);
        api.getApi(valueOfAccountClass(account.getClass())).updateAccount(account, accountDetails);
        accountService.saveAccount(account);
        if (accountDetails.getId() == null) {
            Optional<User> user = userService.getUser(userDetails.getId());
            if (user.isPresent()) {
                user.get().getAccounts().add(account);
                userService.saveUser(user.get());
            }
        }
        return ofNullable(accountToDetails(account));
    }

    private Account getOrCreateAccount(AccountDetails accountDetails) {
        if (accountDetails.getId() == null) {
            return create(accountDetails.getService());
        }
        return accountService.getAccount(accountDetails.getId()).orElse(create(accountDetails.getService()));
    }

    private <T extends AccountDetails> T accountToDetails(Account account) {
        if (account == null) {
            return null;
        }
        return (T) api.getApi(valueOfAccountClass(account.getClass())).getAccountDetailsFromAccount(account);
    }

    @Override
    public void deleteAccount(UserDetails userDetails, AccountDetails accountDetails) {
        Optional<Account> account = accountService.getAccount(accountDetails.getId());
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent() && account.isPresent()) {
            user.get().getAccounts().removeIf(a -> a.getId().equals(account.get().getId()));
            userService.saveUser(user.get());
            accountService.deleteAccount(account.get());
        }
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