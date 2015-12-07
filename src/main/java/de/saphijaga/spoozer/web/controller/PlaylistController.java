package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.PlaylistService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.TracklistDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddPlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.PlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.TrackRequest;
import de.saphijaga.spoozer.web.domain.response.GetPlaylistResponse;
import de.saphijaga.spoozer.web.domain.response.GetPlaylistsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

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
    public GetPlaylistsResponse deletePlaylist(UserDetails user, @Payload PlaylistRequest request) {
        playlistService.deletePlaylist(user, request.getId());
        return getPlaylists(user);
    }

    @MessageMapping("/addSongToPlaylist")
    @SendToUser("/addedSongToPlaylist")
    public void addSongToPlaylist(UserDetails user, @Payload TrackRequest request) {
        TrackDetails trackDetails = api.getApi(request.getService()).getTrack(user, request.getTrackId());
        playlistService.addSongToPlaylist(user, request.getPlaylistId(), trackDetails);
    }

    @MessageMapping("/deleteSongFromPlaylist")
    @SendToUser("/setPlaylist")
    public GetPlaylistResponse deleteSongFromPlaylist(UserDetails user, @Payload TrackRequest request) {
        playlistService.deleteSongFromPlaylist(user, request);
        Optional<TracklistDetails> playlist = playlistService.getPlaylist(user, request.getPlaylistId());
        return new GetPlaylistResponse(playlist.orElse(null));
    }

    @MessageMapping("/getPlaylist")
    @SendToUser("/setPlaylist")
    public GetPlaylistResponse getPlaylist(UserDetails user, @Payload PlaylistRequest request) {
        Optional<TracklistDetails> playlist = playlistService.getPlaylist(user, request.getId());
        return new GetPlaylistResponse(playlist.orElse(null));
    }
}
