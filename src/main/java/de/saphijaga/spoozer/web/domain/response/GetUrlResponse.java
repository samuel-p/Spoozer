package de.saphijaga.spoozer.web.domain.response;

/**
 * Created by samuel on 12.11.15.
 */
public class GetUrlResponse {
    private String url;

    public GetUrlResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}