package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 05.04.16.
 */
public class GetTrackDetailsRequest {
    private String id;
    private StreamingService service;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StreamingService getService() {
        return service;
    }

    public void setService(StreamingService service) {
        this.service = service;
    }
}