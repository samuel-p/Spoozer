package de.saphijaga.spoozer.service;

import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;

import java.util.List;

/**
 * Created by samuel on 14.11.15.
 */
public interface Api {
    StreamingService getService();

    AccountDetails updateAccountDetails(UserDetails userDetails);

    List<TrackDetails> getSearchResult(UserDetails user, String search);
}