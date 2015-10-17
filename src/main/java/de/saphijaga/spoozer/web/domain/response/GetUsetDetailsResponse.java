package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.UserDetails;

/**
 * Created by samuel on 17.10.15.
 */
public class GetUsetDetailsResponse {
    private UserDetails userDetails;

    public GetUsetDetailsResponse(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}