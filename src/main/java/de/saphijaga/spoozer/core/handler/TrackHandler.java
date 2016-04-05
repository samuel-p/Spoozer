package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.TrackService;
import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.PlaylistPersistenceService;
import de.saphijaga.spoozer.persistence.service.TrackPersistenceService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddHTrackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by samuel on 05.04.16.
 */
@Component
public class TrackHandler implements TrackService {
    @Autowired
    private UserPersistenceService userService;
    @Autowired
    private PlaylistPersistenceService playlistService;
    @Autowired
    private TrackPersistenceService trackService;
    @Autowired
    private ApiService api;

    @Override
    public Optional<TrackDetails> getTrack(String id) {
        return trackService.getTrack(id).map(this::toTrackDetails);
    }

    @Override
    public Optional<TrackDetails> getTrack(StreamingService service, String id) {
        return trackService.getTrack(service, id).map(this::toTrackDetails);
    }

    @Override
    public Optional<TrackDetails> addTrackToPlaylist(String playlistId, TrackDetails trackDetails) {
        Optional<Playlist> playlist = playlistService.getPlaylist(playlistId);
        if (playlist.isPresent()) {
            List<Track> tracks = playlist.map(Playlist::getTracks).orElse(emptyList());
            Track track = trackService.getTrack(trackDetails.getService(), trackDetails.getId()).orElse(toTrack(trackDetails));

            tracks.add(track);
            playlistService.savePlaylist(playlist.get());
        }
        return null;
    }

    @Override
    public Optional<TrackDetails> addTrackToHistory(UserDetails userDetails, AddHTrackRequest request) {
        Optional<User> user = userService.getUser(userDetails.getId());
        Track song = trackService.getTrack(request.getService(), request.getId()).orElse(toTrack(api.getApi(request.getService()).getTrack(userDetails, request.getId())));
        Optional<Track> track = trackService.saveTrack(song);
        user.get().getHistory().addSong(song);
        userService.saveUser(user.get());
        return track.map(this::toTrackDetails);
    }

    @Override
    public List<TrackDetails> getTrackOfPlaylist(String playlistId) {
        return playlistService.getPlaylist(playlistId).map(Playlist::getTracks).orElse(emptyList()).stream().map(this::toTrackDetails).collect(toList());
    }

    private Track toTrack(TrackDetails trackDetails) {
        Track track = new Track();
        track.setServiceId(trackDetails.getId());
        track.setService(trackDetails.getService());
        track.setTitle(trackDetails.getTitle());
        track.setDurationInMillis(trackDetails.getDurationInMillis());
        track.setInterpret(trackDetails.getInterpret());
        track.setAlbum(trackDetails.getAlbum());
        track.setCoverUrl(trackDetails.getCoverUrl());
        track.setUrl(trackDetails.getUrl());
        track.setExternalUrl(trackDetails.getUrl());
        return track;
    }

    private TrackDetails toTrackDetails(Track track) {
        TrackDetails trackDetails = new TrackDetails();
        trackDetails.setId(track.getServiceId());
        trackDetails.setService(track.getService());
        trackDetails.setTitle(track.getTitle());
        trackDetails.setDurationInMillis(track.getDurationInMillis());
        trackDetails.setInterpret(track.getInterpret());
        trackDetails.setAlbum(track.getAlbum());
        trackDetails.setCoverUrl(track.getCoverUrl());
        trackDetails.setUrl(track.getUrl());
        trackDetails.setExternalUrl(track.getUrl());
        return trackDetails;
    }
}
