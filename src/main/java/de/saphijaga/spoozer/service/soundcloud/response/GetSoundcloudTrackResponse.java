package de.saphijaga.spoozer.service.soundcloud.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by samuel on 31.03.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSoundcloudTrackResponse {
    private String id;
    private String title;
    private GetSoundcloudProfileResponse user;
    @JsonProperty("label_name")
    private String labelName;
    @JsonProperty("artwork_url")
    private String artworkUrl;
    @JsonProperty("duration")
    private long durationInMillis;
    @JsonProperty("stream_url")
    private String streamUrl;
    private String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GetSoundcloudProfileResponse getUser() {
        return user;
    }

    public void setUser(GetSoundcloudProfileResponse user) {
        this.user = user;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
