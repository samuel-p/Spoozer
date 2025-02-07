package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.PlaylistService;
import de.saphijaga.spoozer.core.service.TrackService;
import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.PlaylistPersistenceService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.PlaylistDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.TracklistDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddPlaylistRequest;
import de.saphijaga.spoozer.web.domain.request.TrackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

/**
 * Created by xce35l5 on 04.12.2015.
 */
@Component
public class PlaylistHandler implements PlaylistService {
    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private PlaylistPersistenceService playlistService;
    @Autowired
    private TrackService trackService;

    @Override
    public void addPlaylist(UserDetails userDetails, AddPlaylistRequest request) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
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
    public void addSongToPlaylist(UserDetails userDetails, String id, TrackDetails trackDetails) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            trackService.addTrackToPlaylist(id, trackDetails);
        }
    }

    @Override
    public void deletePlaylist(UserDetails userDetails, String id) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            Optional<Playlist> playlist = playlistService.getPlaylist(id);
            if (playlist.isPresent()) {
                playlistService.deletePlaylist(playlist.get());
            }
            user.get().getPlaylists().removeIf(p -> p.getId().equals(id));
            userService.saveUser(user.get());
        }
    }

    @Override
    public Optional<TracklistDetails> getPlaylist(UserDetails userDetails, String id) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            Optional<Playlist> playlist = playlistService.getPlaylist(id);
            if (playlist.isPresent()) {
                return of(toTracklistDetails(playlist.get()));
            }
        }
        return empty();
    }

    private TracklistDetails toTracklistDetails(Playlist playlist) {
        TracklistDetails tracklist = new TracklistDetails();
        tracklist.setId(playlist.getId());
        tracklist.setName(playlist.getName());
        tracklist.setTracks(trackService.getTrackOfPlaylist(playlist.getId()));
        return tracklist;
    }

    @Override
    public List<PlaylistDetails> getPlaylists(UserDetails userDetails) {
        Optional<User> user = userService.getUser(userDetails.getId());
        return user.map(User::getPlaylists).orElse(emptyList()).stream().map(this::toPlaylistDetails).collect(toList());
    }

    private PlaylistDetails toPlaylistDetails(Playlist playlist) {
        PlaylistDetails details = new PlaylistDetails();
        details.setName(playlist.getName());
        details.setId(playlist.getId());
        details.setTrackCount(playlist.getTracks().size());
        return details;
    }

    @Override
    public void deleteSongFromPlaylist(UserDetails userDetails, TrackRequest request) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            Optional<Playlist> playlist = playlistService.getPlaylist(request.getPlaylistId());
            playlist.map(Playlist::getTracks).orElse(emptyList()).removeIf(track -> track.getServiceId().equals(request.getTrackId()) && track.getService().equals(request.getService()));
            playlistService.savePlaylist(playlist.get());
        }
    }
}
