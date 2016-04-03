package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.domain.request.GetSearchResultRequest;
import de.saphijaga.spoozer.web.domain.response.GetTrackResultResponse;
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
    public GetTrackResultResponse getSearchResult(UserDetails user, @Payload GetSearchResultRequest request) {
        return getTrackResult(api -> api.getSearchResult(user, request.getSearch()));
    }

    @MessageMapping("/getChartTracks")
    @SendToUser("/setChartTracks")
    public GetTrackResultResponse getChartTracks(UserDetails user) {
        return getTrackResult(api -> api.getChartTracks(user));
    }

    @MessageMapping("/getNewReleasedTracks")
    @SendToUser("/setNewReleasedTracks")
    public GetTrackResultResponse getNewReleasedTracks(UserDetails user) {
        return getTrackResult(api -> api.getNewReleasedTracks(user));
    }

    @MessageMapping("/getTrendingTracks")
    @SendToUser("/setTrendingTracks")
    public GetTrackResultResponse getTrendingTracks(UserDetails user) {
        return getTrackResult(api -> api.getTrendingTracks(user));
    }

    @MessageMapping("/getFeaturedTracks")
    @SendToUser("/setFeaturedTracks")
    public GetTrackResultResponse getFeaturedTracks(UserDetails user) {
        return getTrackResult(api -> api.getFeaturedTracks(user));
    }

    private interface ApiTrackResult {
        List<TrackDetails> getTrackResult(Api api);
    }

    private GetTrackResultResponse getTrackResult(ApiTrackResult result) {
        Map<String, List<TrackDetails>> searchResult = new HashMap<>();
        apiService.getApis().forEach(api -> {
            List<TrackDetails> apiResult = result.getTrackResult(api);
            if (!apiResult.isEmpty()) {
                searchResult.put(api.getService().name().toLowerCase(), apiResult);
            }
        });
        return new GetTrackResultResponse(searchResult);
    }
}