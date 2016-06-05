package de.saphijaga.spoozer.web.domain.response;

import java.util.Map;

/**
 * Created by samuel on 23.05.16.
 */
public class SettingsResponse {
    private Map<String, Object> settings;

    public SettingsResponse(Map<String, Object> settings) {
        this.settings = settings;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }
}
