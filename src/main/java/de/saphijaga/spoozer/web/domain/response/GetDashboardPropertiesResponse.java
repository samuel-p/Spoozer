package de.saphijaga.spoozer.web.domain.response;

import java.util.Map;

/**
 * Created by samuel on 03.04.16.
 */
public class GetDashboardPropertiesResponse {
    private Map<String, Object> properties;

    public GetDashboardPropertiesResponse(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}