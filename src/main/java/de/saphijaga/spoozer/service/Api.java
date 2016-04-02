package de.saphijaga.spoozer.service;

import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;

import java.util.List;

/**
 * Created by samuel on 14.11.15.
 */
public interface Api<ApiAccount extends Account, ApiAccountDetails extends AccountDetails, ApiAccountAccessDetails extends AccountAccessDetails> {
    StreamingService getService();

    ApiAccountDetails getAccountDetailsFromAccount(ApiAccount account);

    ApiAccountAccessDetails getAccountAccessDetailsFromAccount(ApiAccount account);

    void updateAccount(ApiAccount account, ApiAccountDetails details);

    void updateAccount(ApiAccount account, ApiAccountAccessDetails accessDetails);

    ApiAccountDetails updateAccountDetails(UserDetails userDetails);

    List<TrackDetails> getSearchResult(UserDetails user, String search);

    TrackDetails getTrack(UserDetails user, String id);
}