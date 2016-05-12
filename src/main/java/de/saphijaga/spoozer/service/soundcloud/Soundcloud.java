package de.saphijaga.spoozer.service.soundcloud;

/**
 * Created by samuel on 31.03.16.
 */
public class Soundcloud {
    public static final String CLIENT_ID = "aa62bd9326ec7632872b47c545652573";
    public static final String CLIENT_SECRET = "910640feff5d44a419aa3fd0817cd7cf";
    public static final String LOGIN_URL = "https://soundcloud.com/connect";
    public static final String TOKEN_URL = "https://api.soundcloud.com/oauth2/token";
    public static final String API_URL = "https://api.soundcloud.com";
    public static final String API_URL_2 = "https://api-v2.soundcloud.com";
    public static final String REDIRECT_URL_PATH = "/soundcloud/callback";
    public static final String SCOPE = "non-expiring";
    public static final String STATE = "soundcloud-state";

    private Soundcloud() {
    }
}