package de.saphijaga.spoozer.service;

import de.saphijaga.spoozer.core.service.AccountAccessService;
import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Created by samuel on 09.05.16.
 */
public abstract class BaseApi<ApiAccount extends Account, ApiAccountDetails extends AccountDetails, ApiAccountAccessDetails extends AccountAccessDetails> implements Api<ApiAccount, ApiAccountDetails, ApiAccountAccessDetails> {
    private static final String ACCESS_DETAILS_EXPIRED = "AccessDetails expired";

    private static Logger logger = LoggerFactory.getLogger(BaseApi.class);

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

    protected abstract void refreshAccessDetails(UserDetails user, ApiAccountAccessDetails accessDetails);

    @Override
    public List<TrackDetails> getSearchResult(UserDetails user, String search) {
        return getSearchResult(user, search, true);
    }

    private List<TrackDetails> getSearchResult(UserDetails user, String search, boolean retry) {
        Optional<ApiAccountAccessDetails> accessDetails = accessService.getAccessDetails(user, getService());
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        try {
            return getSearchResult(accessDetails.get(), search);
        } catch (AccessDetailsExpiredException e) {
            logger.warn(ACCESS_DETAILS_EXPIRED, e);
            if (retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getSearchResult(user, search, false);
            }
        }
        return emptyList();
    }

    protected abstract List<TrackDetails> getSearchResult(ApiAccountAccessDetails accessDetails, String search) throws AccessDetailsExpiredException;

    @Override
    public TrackDetails getTrack(UserDetails user, String id) {
        return getTrack(user, id, true);
    }

    private TrackDetails getTrack(UserDetails user, String id, boolean retry) {
        Optional<ApiAccountAccessDetails> accessDetails = accessService.getAccessDetails(user, getService());
        if (!accessDetails.isPresent()) {
            return null;
        }
        try {
            return getTrack(accessDetails.get(), id);
        } catch (AccessDetailsExpiredException e) {
            logger.warn(ACCESS_DETAILS_EXPIRED, e);
            if (retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getTrack(user, id, false);
            }
        }
        return null;
    }

    protected abstract TrackDetails getTrack(ApiAccountAccessDetails accessDetails, String id) throws AccessDetailsExpiredException;

    @Override
    public List<TrackDetails> getChartTracks(UserDetails user) {
        return getChartTracks(user, true);
    }

    private List<TrackDetails> getChartTracks(UserDetails user, boolean retry) {
        Optional<ApiAccountAccessDetails> accessDetails = accessService.getAccessDetails(user, getService());
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        try {
            return getChartTracks(accessDetails.get());
        } catch (AccessDetailsExpiredException e) {
            logger.warn(ACCESS_DETAILS_EXPIRED, e);
            if (retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getChartTracks(user, false);
            }
        }
        return emptyList();
    }

    protected List<TrackDetails> getChartTracks(ApiAccountAccessDetails accessDetails) throws AccessDetailsExpiredException {
        return emptyList();
    }

    @Override
    public List<TrackDetails> getNewReleasedTracks(UserDetails user) {
        return getNewReleasedTracks(user, true);
    }

    private List<TrackDetails> getNewReleasedTracks(UserDetails user, boolean retry) {
        Optional<ApiAccountAccessDetails> accessDetails = accessService.getAccessDetails(user, getService());
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        try {
            return getNewReleasedTracks(accessDetails.get());
        } catch (AccessDetailsExpiredException e) {
            logger.warn(ACCESS_DETAILS_EXPIRED, e);
            if (retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getNewReleasedTracks(user, false);
            }
        }
        return emptyList();
    }

    protected List<TrackDetails> getNewReleasedTracks(ApiAccountAccessDetails accessDetails) throws AccessDetailsExpiredException {
        return emptyList();
    }

    @Override
    public List<TrackDetails> getTrendingTracks(UserDetails user) {
        return getTrendingTracks(user, true);
    }

    private List<TrackDetails> getTrendingTracks(UserDetails user, boolean retry) {
        Optional<ApiAccountAccessDetails> accessDetails = accessService.getAccessDetails(user, getService());
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        try {
            return getTrendingTracks(accessDetails.get());
        } catch (AccessDetailsExpiredException e) {
            logger.warn(ACCESS_DETAILS_EXPIRED, e);
            if (retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getTrendingTracks(user, false);
            }
        }
        return emptyList();
    }

    protected List<TrackDetails> getTrendingTracks(ApiAccountAccessDetails accessDetails) throws AccessDetailsExpiredException {
        return emptyList();
    }

    @Override
    public List<TrackDetails> getFeaturedTracks(UserDetails user) {
        return getFeaturedTracks(user, true);
    }

    private List<TrackDetails> getFeaturedTracks(UserDetails user, boolean retry) {
        Optional<ApiAccountAccessDetails> accessDetails = accessService.getAccessDetails(user, getService());
        if (!accessDetails.isPresent()) {
            return emptyList();
        }
        try {
            return getFeaturedTracks(accessDetails.get());
        } catch (AccessDetailsExpiredException e) {
            logger.warn(ACCESS_DETAILS_EXPIRED, e);
            if (retry) {
                refreshAccessDetails(user, accessDetails.get());
                return getFeaturedTracks(user, false);
            }
        }
        return emptyList();
    }

    protected List<TrackDetails> getFeaturedTracks(ApiAccountAccessDetails accessDetails) throws AccessDetailsExpiredException {
        return emptyList();
    }
}