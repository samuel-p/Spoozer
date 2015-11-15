package de.saphijaga.spoozer.web.details;

import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 13.11.15.
 */
public abstract class AccountDetails {
    private StreamingService service;
    private String serviceName;
    private String id;
    private String username;
    private String url;

    public AccountDetails(StreamingService service) {
        this.service = service;
        this.serviceName = service.getName();
    }

    public StreamingService getService() {
        return service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}