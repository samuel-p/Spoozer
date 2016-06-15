package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserPropertiesService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetDashboardPropertiesResponse;
import de.saphijaga.spoozer.web.domain.response.GetServiceTrackResultResponse;
import de.saphijaga.spoozer.web.domain.response.GetTrackResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * Created by samuel on 03.04.16.
 */
@RestController
public class DashboardController {
    @Autowired
    private ApiService apiService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPropertiesService propertiesService;

    @MessageMapping("/getDashboardProperties")
    @SendToUser("/setDashboardProperties")
    public GetDashboardPropertiesResponse getDashboardProperties(UserDetails user) {
        Map<String, Object> properties = new HashMap<>();
        stream(StreamingService.values()).forEach(service -> properties.put(service.getName().toLowerCase(), propertiesService.hasAccount(user, service)));
        properties.put("services", propertiesService.getServicesCount(user));
        properties.put("playlists", propertiesService.getPlaylistCount(user));
        properties.put("tracks", propertiesService.getTrackCount(user));
        return new GetDashboardPropertiesResponse(properties);
    }

    @MessageMapping("/getHistoryTracks")
    @SendToUser("/setHistoryTracks")
    public GetTrackResultResponse getHistoryTracks(UserDetails user) {
        List<TrackDetails> historyTracks = userService.getHistoryMap(user).entrySet().stream().sorted((entry0, entry1) -> entry1.getKey().compareTo(entry0.getKey())).map(Map.Entry::getValue).limit(15).collect(toList());
        return new GetTrackResultResponse(historyTracks);
    }

    @MessageMapping("/getChartTracks")
    @SendToUser("/setChartTracks")
    public GetServiceTrackResultResponse getChartTracks(UserDetails user) {
        return getTrackResult(api -> api.getChartTracks(user));
    }

    @MessageMapping("/getNewReleasedTracks")
    @SendToUser("/setNewReleasedTracks")
    public GetServiceTrackResultResponse getNewReleasedTracks(UserDetails user) {
        return getTrackResult(api -> api.getNewReleasedTracks(user));
    }

    @MessageMapping("/getTrendingTracks")
    @SendToUser("/setTrendingTracks")
    public GetServiceTrackResultResponse getTrendingTracks(UserDetails user) {
        return getTrackResult(api -> api.getTrendingTracks(user));
    }

    @MessageMapping("/getFeaturedTracks")
    @SendToUser("/setFeaturedTracks")
    public GetServiceTrackResultResponse getFeaturedTracks(UserDetails user) {
        return getTrackResult(api -> api.getFeaturedTracks(user));
    }

    @FunctionalInterface
    private interface ApiTrackResult {
        List<TrackDetails> getTrackResult(Api api);
    }

    private GetServiceTrackResultResponse getTrackResult(ApiTrackResult result) {
        Map<String, List<TrackDetails>> searchResult = new HashMap<>();
        apiService.getApis().forEach(api -> {
            List<TrackDetails> apiResult = result.getTrackResult(api);
            if (!apiResult.isEmpty()) {
                searchResult.put(api.getService().name().toLowerCase(), apiResult);
            }
        });
        return new GetServiceTrackResultResponse(searchResult);
    }
}