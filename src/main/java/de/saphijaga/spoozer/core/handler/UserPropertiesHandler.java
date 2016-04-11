package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.core.service.PlaylistService;
import de.saphijaga.spoozer.core.service.UserPropertiesService;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.web.details.PlaylistDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by samuel on 03.04.16.
 */
@Component
public class UserPropertiesHandler implements UserPropertiesService {
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private AccountService accountService;

    @Override
    public boolean hasAccount(UserDetails user, StreamingService service) {
        return accountService.getAccountList(user).stream().anyMatch(account -> account.getService() == service);
    }

    @Override
    public int getServicesCount(UserDetails user) {
        return accountService.getAccountList(user).size();
    }

    @Override
    public int getPlaylistCount(UserDetails user) {
        return playlistService.getPlaylists(user).size();
    }

    @Override
    public int getTrackCount(UserDetails user) {
        return playlistService.getPlaylists(user).stream().mapToInt(PlaylistDetails::getTrackCount).sum();
    }
}
