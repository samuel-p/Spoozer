package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.domain.request.GetSearchResultRequest;
import de.saphijaga.spoozer.web.domain.response.GetServiceTrackResultResponse;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by samuel on 08.11.15.
 */
@RestController
public class TrackController {
    @Autowired
    private ApiService apiService;

    @MessageMapping("/getSearchResult")
    @SendToUser("/setSearchResult")
    public GetServiceTrackResultResponse getSearchResult(UserDetails user, @Payload GetSearchResultRequest request) {
        Map<String, List<TrackDetails>> searchResult = new HashMap<>();
        apiService.getApis().forEach(api -> {
            List<TrackDetails> apiResult = api.getSearchResult(user, request.getSearch());
            if (!apiResult.isEmpty()) {
                searchResult.put(api.getService().name().toLowerCase(), apiResult);
            }
        });
        return new GetServiceTrackResultResponse(searchResult);
    }
}