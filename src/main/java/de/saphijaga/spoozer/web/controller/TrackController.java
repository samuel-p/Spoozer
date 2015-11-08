package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.domain.request.GetSearchResultRequest;
import de.saphijaga.spoozer.web.domain.response.GetSearchResultResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by samuel on 08.11.15.
 */
@RestController
public class TrackController {
    @MessageMapping("/getSearchResult")
    @SendTo("/setSearchResult")
    public GetSearchResultResponse getSearchResult(GetSearchResultRequest request) {
        System.out.println("search: " + request.getSearch());
        Map<String, List<TrackDetails>> searchResult = new HashMap<>();
        searchResult.put("spotify", asList(getRandomSearchResult(), getRandomSearchResult(), getRandomSearchResult()));
        searchResult.put("youtube", asList(getRandomSearchResult(), getRandomSearchResult()));
        return new GetSearchResultResponse(searchResult);
    }

    public TrackDetails getRandomSearchResult() {
        TrackDetails trackDetails = new TrackDetails();
        trackDetails.setId(UUID.randomUUID().toString());
        return trackDetails;
    }
}