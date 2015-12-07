package de.saphijaga.spoozer.persistence.domain;

import de.saphijaga.spoozer.service.StreamingService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by samuel on 27.10.15.
 */
@Document
public class Track {
    @Id
    private String id;
    private String streamingId;
    private StreamingService streamingService;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreamingId() {
        return streamingId;
    }

    public void setStreamingId(String streamingId) {
        this.streamingId = streamingId;
    }

    public StreamingService getStreamingService() {
        return streamingService;
    }

    public void setStreamingService(StreamingService streamingService) {
        this.streamingService = streamingService;
    }
}