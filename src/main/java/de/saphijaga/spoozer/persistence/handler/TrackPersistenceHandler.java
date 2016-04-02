package de.saphijaga.spoozer.persistence.handler;

import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.persistence.repository.TrackRepository;
import de.saphijaga.spoozer.persistence.service.TrackPersistenceService;
import de.saphijaga.spoozer.service.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 * Created by samuel on 04.12.2015.
 */
@Component
public class TrackPersistenceHandler implements TrackPersistenceService {
    @Autowired
    private TrackRepository trackRepository;

    @Override
    public Optional<Track> saveTrack(Track track) {
        if(track.getId()==null){
            track.setId(UUID.randomUUID().toString());
        }
        return ofNullable(trackRepository.save(track));
    }

    @Override
    public void deleteTrack(Track track) {
        trackRepository.delete(track);
    }

    @Override
    public Optional<Track> getTrack(String id) {
        return ofNullable(trackRepository.findOne(id));
    }

    @Override
    public Optional<Track> getTrack(StreamingService service, String serviceId) {
        return ofNullable(trackRepository.findOneByServiceAndServiceId(service, serviceId));
    }
}
