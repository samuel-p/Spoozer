package de.saphijaga.spoozer.persistence.repository;

import de.saphijaga.spoozer.persistence.domain.Playlist;
import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.service.StreamingService;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by samuel on 27.10.15.
 */
public interface TrackRepository extends CrudRepository<Track, String> {
    Track findOneByServiceAndServiceId(StreamingService service, String serviceId);
}
