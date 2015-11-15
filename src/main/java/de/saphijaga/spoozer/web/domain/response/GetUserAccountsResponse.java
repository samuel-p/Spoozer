package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.AccountDetails;

import java.util.Map;

/**
 * Created by samuel on 14.11.15.
 */
public class GetUserAccountsResponse {
    private Map<String, AccountDetails> userAccounts;

    public GetUserAccountsResponse(Map<String, AccountDetails> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public Map<String, AccountDetails> getUserAccounts() {
        return userAccounts;
    }
}