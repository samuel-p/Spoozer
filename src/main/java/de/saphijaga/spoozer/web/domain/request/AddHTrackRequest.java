package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.service.StreamingService;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by jan-ericgaidusch on 31.03.16.
 */
public class AddHTrackRequest {

    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    @NotEmpty
    private StreamingService service;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StreamingService getService() {
        return service;
    }

    public void setService(StreamingService service) {
        this.service = service;
    }
}
