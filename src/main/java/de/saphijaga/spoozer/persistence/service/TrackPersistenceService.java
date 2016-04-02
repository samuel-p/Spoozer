package de.saphijaga.spoozer.persistence.service;

import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.service.StreamingService;

import java.util.Optional;

/**
 * Created by samuel on 02.04.16.
 */
public interface TrackPersistenceService {
    Optional<Track> saveTrack(Track track);

    void deleteTrack(Track track);

    Optional<Track> getTrack(String id);

    Optional<Track> getTrack(StreamingService service, String serviceId);
}
