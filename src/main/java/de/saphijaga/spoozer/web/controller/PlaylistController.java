package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.PlaylistService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
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
    private UserService userService;

    @Autowired
    private UserPersistenceService userPersistenceService;

    @Autowired
    private PlaylistService playlistService;

    @MessageMapping("/addPlaylist")
    @SendToUser("/playlistAdded")
    public GetUserDetailsResponse addPlaylist(UserDetails user, @Payload @Valid AddPlaylistRequest addPlaylistRequest){
        System.out.println(addPlaylistRequest.getName());
        if(userService.getUserDetails(user.getId()).isPresent()) {
            playlistService.addPlaylist(user, addPlaylistRequest);
            System.out.println("playlist added");
        }
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/addSongToPlaylist")
    @SendToUser("/addedSongToPlaylist")
    public void addSongToPlaylist(UserDetails user, SongRequest request){
        if(userService.getUserDetails(user.getId()).isPresent()){

        }
    }
    @MessageMapping("/getUserPlaylists")
    @SendToUser("/getPlaylists")
    public GetPlaylistsResponse getPlaylists(UserDetails user){
        if(userService.getUserDetails(user.getId()).isPresent()){
            GetPlaylistsResponse response = new GetPlaylistsResponse();
            System.out.println("getting playlists");
            response.setPlaylists(playlistService.getPlaylists(user));
            return response;
        }
        return new GetPlaylistsResponse();
    }
}
