package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.web.details.UserDetails;

/**
 * Created by samuel on 03.04.16.
 */
public interface UserPropertiesService {
    boolean hasAccount(UserDetails user, StreamingService service);

    int getPlaylistCount(UserDetails user);

    int getTrackCount(UserDetails user);

    int getServicesCount(UserDetails user);
}