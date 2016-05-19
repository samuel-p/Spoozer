package de.saphijaga.spoozer.persistence.domain;

import de.saphijaga.spoozer.service.StreamingService;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by samuel on 27.10.15.
 */
@Document
public class Track {
    @Id
    private String id;
    private String serviceId;
    private StreamingService service;
    private String title;
    private long durationInMillis;
    private String interpret;
    private String album;
    private String coverUrl;
    private String externalUrl;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public StreamingService getService() {
        return service;
    }

    public void setService(StreamingService service) {
        this.service = service;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (durationInMillis != track.durationInMillis) return false;
        if (id != null ? !id.equals(track.id) : track.id != null) return false;
        if (serviceId != null ? !serviceId.equals(track.serviceId) : track.serviceId != null) return false;
        if (service != track.service) return false;
        if (title != null ? !title.equals(track.title) : track.title != null) return false;
        if (interpret != null ? !interpret.equals(track.interpret) : track.interpret != null) return false;
        if (album != null ? !album.equals(track.album) : track.album != null) return false;
        if (coverUrl != null ? !coverUrl.equals(track.coverUrl) : track.coverUrl != null) return false;
        if (externalUrl != null ? !externalUrl.equals(track.externalUrl) : track.externalUrl != null) return false;
        return url != null ? url.equals(track.url) : track.url == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (int) (durationInMillis ^ (durationInMillis >>> 32));
        result = 31 * result + (interpret != null ? interpret.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (coverUrl != null ? coverUrl.hashCode() : 0);
        result = 31 * result + (externalUrl != null ? externalUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}