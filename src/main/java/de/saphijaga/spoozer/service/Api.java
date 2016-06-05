package de.saphijaga.spoozer.service;

import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Created by samuel on 14.11.15.
 */
public interface Api<A extends Account, B extends AccountDetails, C extends AccountAccessDetails> {
    StreamingService getService();

    B getAccountDetailsFromAccount(A account);

    C getAccountAccessDetailsFromAccount(A account);

    void updateAccount(A account, B details);

    void updateAccount(A account, C accessDetails);

    B updateAccountDetails(UserDetails userDetails);

    List<TrackDetails> getSearchResult(UserDetails user, String search);

    TrackDetails getTrack(UserDetails user, String id);

    default List<TrackDetails> getChartTracks(UserDetails user) {
        return emptyList();
    }

    default List<TrackDetails> getNewReleasedTracks(UserDetails user) {
        return emptyList();
    }

    default List<TrackDetails> getTrendingTracks(UserDetails user) {
        return emptyList();
    }

    default List<TrackDetails> getFeaturedTracks(UserDetails user) {
        return emptyList();
    }
}