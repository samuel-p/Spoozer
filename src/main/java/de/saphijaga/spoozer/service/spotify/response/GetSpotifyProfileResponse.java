package de.saphijaga.spoozer.service.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by samuel on 13.11.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSpotifyProfileResponse {
    private String id;
    private String country;
    @JsonProperty("display_name")
    private String displayname;
    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }
}