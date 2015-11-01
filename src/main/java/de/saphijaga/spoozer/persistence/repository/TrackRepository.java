package de.saphijaga.spoozer.persistence.repository;

import de.saphijaga.spoozer.persistence.domain.Track;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by samuel on 27.10.15.
 */
public interface TrackRepository extends CrudRepository<Track, String> {
}