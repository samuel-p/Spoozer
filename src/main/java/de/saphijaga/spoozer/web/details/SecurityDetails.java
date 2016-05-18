package de.saphijaga.spoozer.web.details;

/**
 * Created by samuel on 18.10.15.
 */
public class SecurityDetails {
    private boolean isConnectionSecure;

    public boolean isConnectionSecure() {
        return isConnectionSecure;
    }

    public void setIsConnectionSecure(boolean isConnectionSecure) {
        this.isConnectionSecure = isConnectionSecure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityDetails that = (SecurityDetails) o;

        return isConnectionSecure == that.isConnectionSecure;
    }

    @Override
    public int hashCode() {
        return (isConnectionSecure ? 1 : 0);
    }
}