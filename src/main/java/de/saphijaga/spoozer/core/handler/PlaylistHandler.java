package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.PlaylistService;
import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.PlaylistPersistenceService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.PlaylistDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddPlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.SongRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by xce35l5 on 04.12.2015.
 */
@Component
public class PlaylistHandler implements PlaylistService {

    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private PlaylistPersistenceService playlistService;

    @Override
    public void addPlaylist(UserDetails userDetails, AddPlaylistRequest request) {
        Optional<User> user = userService.getUser(userDetails.getId());

        if(user.isPresent()){
            Playlist playlist = toPlaylist(request);
            Optional<Playlist> savedList = playlistService.savePlaylist(playlist);
            user.get().getPlaylists().add(savedList.get());
            userService.saveUser(user.get());
        }
    }

    private Playlist toPlaylist(AddPlaylistRequest request) {
        Playlist playlist = new Playlist();
        playlist.setName(request.getName());
        return playlist;
    }

    @Override
    public <T extends PlaylistDetails> Optional<T> savePlaylist(UserDetails user, PlaylistDetails playlist) {

        return null;
    }

    @Override
    public void addSongToPlaylist(UserDetails user, SongRequest request) {
        Optional<Playlist> playlist = userService.getUser(user.getId()).get().getPlaylists().stream().filter(p -> p.getName().equals(request.getPlayListName())).findAny();
        Playlist plist = playlist.get();
        System.out.println(request.getTrack().getId());
        List<Track> tracks = plist.getTracks();
        tracks.add(request.getTrack());
        plist.setTracks(tracks);
        playlistService.savePlaylist(plist);
        saveUserPlaylist(user, plist);
   }

    private void saveUserPlaylist(UserDetails userDetails, Playlist plist) {
        User user = userService.getUser(userDetails.getId()).get();
        List<Playlist> playlists = user.getPlaylists();
        playlists.remove(playlists.stream().filter(p -> p.getId().equals(plist.getId())).findAny().get());
        playlists.add(plist);
        user.setPlaylists(playlists);
        userService.saveUser(user);

    }


    @Override
    public void deletePlaylist(UserDetails userDetails, PlaylistDetails playlistDetails) {
        User user = userService.getUser(userDetails.getId()).get();
        Playlist playlist = playlistService.getPlaylist(playlistDetails.getId()).get();
        Playlist dellist = null;
        for(Playlist list : user.getPlaylists()){
            if(list.getId().equals(playlist.getId())){
                dellist = list;
            }
        }
        if(dellist != null){
            user.getPlaylists().remove(dellist);
        }
        userService.saveUser(user);
        playlistService.deletePlaylist(playlist);
    }


    @Override
    public Playlist getPlaylist(UserDetails userDetails, PlaylistDetails playlist) {
        User user = userService.getUser(userDetails.getId()).get();
        return user.getPlaylists().stream().filter(p -> p.getId().equals(playlist.getId())).findAny().get();
    }

    @Override
    public List<PlaylistDetails> getPlaylists(UserDetails user) {
        List<PlaylistDetails> detailslist = new ArrayList<>();
        for (Playlist playlist : userService.getUser(user.getId()).get().getPlaylists()){
            detailslist.add(toPlaylistDetails(playlist));
        }
        return detailslist;
    }

    private PlaylistDetails toPlaylistDetails(Playlist playlist) {
        PlaylistDetails details = new PlaylistDetails();
        details.setName(playlist.getName());
        details.setId(playlist.getId());
        details.setTrackCount(playlist.getTracks().size());
        return details;
    }

    @Override
    public void removeSongFromPlaylist(UserDetails user, SongRequest request) {
        Optional<Playlist> playlist = userService.getUser(user.getId()).get().getPlaylists().stream().filter(p -> p.getName().equals(request.getPlayListName())).findAny();
        Playlist plist = playlist.get();
        List<Track> tracks = plist.getTracks();
        tracks.remove(request.getTrack());
        plist.setTracks(tracks);
        playlistService.savePlaylist(plist);
        //TODO delete empty playlist
        saveUserPlaylist(user, plist);
    }
}
