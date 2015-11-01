package de.saphijaga.spoozer.persistence.service;

import de.saphijaga.spoozer.persistence.domain.Track;

import java.util.Optional;

/**
 * Created by samuel on 27.10.15.
 */
public interface TrackPersistenceService {
    Optional<Track> saveTrack(Track track);

    void deleteTrack(Track track);
}