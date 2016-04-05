package de.saphijaga.spoozer.web.domain.response;

/**
 * Created by samuel on 04.04.16.
 */
public class GetSoundcloudClientIdResponse {
    private String data;

    public GetSoundcloudClientIdResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
