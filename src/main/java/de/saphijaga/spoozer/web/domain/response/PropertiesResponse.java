package de.saphijaga.spoozer.web.domain.response;

import java.util.Map;

/**
 * Created by samuel on 05.04.16.
 */
public class PropertiesResponse {
    private Map<String, Object> properties;

    public PropertiesResponse(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
