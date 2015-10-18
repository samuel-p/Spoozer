package de.saphijaga.spoozer.web.details;

/**
 * Created by samuel on 18.10.15.
 */
public class SecurityDetails {
    private boolean isConnectionSecure;

    public SecurityDetails(boolean isConnectionSecure) {
        this.isConnectionSecure = isConnectionSecure;
    }

    public boolean isConnectionSecure() {
        return isConnectionSecure;
    }
}