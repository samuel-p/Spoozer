package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.TrackDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by samuel on 08.11.15.
 */
public class GetSearchResultResponse {
    private Map<String, List<TrackDetails>> searchResult;

    public GetSearchResultResponse(Map<String, List<TrackDetails>> searchResult) {
        this.searchResult = searchResult;
    }

    public Map<String, List<TrackDetails>> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(Map<String, List<TrackDetails>> searchResult) {
        this.searchResult = searchResult;
    }
}