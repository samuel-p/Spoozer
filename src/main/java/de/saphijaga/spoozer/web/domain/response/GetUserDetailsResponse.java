package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.UserDetails;

/**
 * Created by samuel on 17.10.15.
 */
public class GetUserDetailsResponse {
    private UserDetails userDetails;

    public GetUserDetailsResponse(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}