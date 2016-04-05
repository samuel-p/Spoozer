package de.saphijaga.spoozer.service.soundcloud.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by samuel on 03.04.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSoundcloudChartTracksResponse {
    private List<GetSoundcloudChartTrackResponse> collection;

    public List<GetSoundcloudChartTrackResponse> getCollection() {
        return collection;
    }

    public void setCollection(List<GetSoundcloudChartTrackResponse> collection) {
        this.collection = collection;
    }
}