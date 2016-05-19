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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddHTrackRequest that = (AddHTrackRequest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return service == that.service;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
    }
}
