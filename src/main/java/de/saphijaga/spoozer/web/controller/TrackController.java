package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.GetSearchResultRequest;
import de.saphijaga.spoozer.web.domain.response.GetSearchResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by samuel on 08.11.15.
 */
@RestController
public class TrackController {
    @Autowired
    private ApiService apiService;

    @MessageMapping("/getSearchResult")
    @SendToUser("/setSearchResult")
    public GetSearchResultResponse getSearchResult(UserDetails user, GetSearchResultRequest request) {
        Map<String, List<TrackDetails>> searchResult = new HashMap<>();
        apiService.getApis().forEach(api -> searchResult.put(api.getService().name().toLowerCase(), api.getSearchResult(user, request.getSearch())));
        return new GetSearchResultResponse(searchResult);
    }
}