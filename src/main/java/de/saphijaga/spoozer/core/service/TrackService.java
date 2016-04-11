package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddHTrackRequest;

import java.util.List;
import java.util.Optional;

/**
 * Created by samuel on 05.04.16.
 */
public interface TrackService {
    Optional<TrackDetails> getTrack(String id);

    Optional<TrackDetails> getTrack(StreamingService service, String id);

    Optional<TrackDetails> addTrackToPlaylist(String playlistId, TrackDetails trackDetails);

    Optional<TrackDetails> addTrackToHistory(UserDetails userDetails, AddHTrackRequest request);

    List<TrackDetails> getTrackOfPlaylist(String playlistId);
}