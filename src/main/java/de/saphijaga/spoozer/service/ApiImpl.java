package de.saphijaga.spoozer.service;

import de.saphijaga.spoozer.core.service.AccountAccessService;
import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by samuel on 09.05.16.
 */
public abstract class ApiImpl<ApiAccount extends Account, ApiAccountDetails extends AccountDetails, ApiAccountAccessDetails extends AccountAccessDetails> implements Api<ApiAccount, ApiAccountDetails, ApiAccountAccessDetails> {
    @Autowired
    protected ApiService service;

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected AccountAccessService accessService;

    @PostConstruct
    public void init() {
        service.registerApi(this);
    }
}