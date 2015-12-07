package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.PlaylistService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.PlaylistDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddPlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.SongRequest;
import de.saphijaga.spoozer.web.domain.response.GetPlaylistsResponse;
import de.saphijaga.spoozer.web.domain.response.GetUserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by xce35l5 on 04.12.2015.
 */
@RestController
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ApiService api;

    @MessageMapping("/addPlaylist")
    @SendToUser("/setPlaylists")
    public GetPlaylistsResponse addPlaylist(UserDetails user, @Payload @Valid AddPlaylistRequest addPlaylistRequest) {
        playlistService.addPlaylist(user, addPlaylistRequest);
        return getPlaylists(user);
    }

    @MessageMapping("/getPlaylists")
    @SendToUser("/setPlaylists")
    public GetPlaylistsResponse getPlaylists(UserDetails user) {
        return new GetPlaylistsResponse(playlistService.getPlaylists(user));
    }

    @MessageMapping("/deletePlaylist")
    @SendToUser("/setPlaylists")
    public GetPlaylistsResponse deletePlaylist(UserDetails user, PlaylistDetails playlistDetails) {
        playlistService.deletePlaylist(user, playlistDetails);
        return getPlaylists(user);
    }

    @MessageMapping("/addSongToPlaylist")
    @SendToUser("/addedSongToPlaylist")
    public void addSongToPlaylist(UserDetails user, SongRequest request) {
        TrackDetails trackDetails = api.getApi(request.getStreamingService()).getTrack(user, request.getTrackID());
        System.out.println(request.getTrackID());
        System.out.println(request.getPlayListID());
        System.out.println(request.getStreamingService());
        playlistService.addSongToPlaylist(user, request);
    }

    @MessageMapping("/getPlaylist")
    @SendToUser("/playPlaylist")
    public Playlist getPlaylist(UserDetails user, PlaylistDetails details) {
        return playlistService.getPlaylist(user, details);
    }
}
