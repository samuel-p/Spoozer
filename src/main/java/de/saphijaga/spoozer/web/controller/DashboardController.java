package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserPropertiesService;
import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetDashboardPropertiesResponse;
import de.saphijaga.spoozer.web.domain.response.GetTrackResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;

/**
 * Created by samuel on 03.04.16.
 */
@RestController
public class DashboardController {
    @Autowired
    private ApiService apiService;

    @Autowired
    private UserPropertiesService userService;

    @MessageMapping("/getDashboardProperties")
    @SendToUser("/setDashboardProperties")
    public GetDashboardPropertiesResponse getDashboardProperties(UserDetails user) {
        Map<String, Object> properties = new HashMap<>();
        stream(StreamingService.values()).forEach(service ->  {
            properties.put(service.getName().toLowerCase(), userService.hasAccount(user, service));
        });
        properties.put("services", userService.getServicesCount(user));
        properties.put("playlists", userService.getPlaylistCount(user));
        properties.put("tracks", userService.getTrackCount(user));
        return new GetDashboardPropertiesResponse(properties);
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