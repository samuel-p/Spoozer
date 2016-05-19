package de.saphijaga.spoozer.web.details;

import de.saphijaga.spoozer.service.StreamingService;

/**
 * Created by samuel on 27.10.15.
 */
public class TrackDetails {
    private StreamingService service;
    private String id;
    private String title;
    private long durationInMillis;
    private String interpret;
    private String album;
    private String coverUrl;
    private String externalUrl;
    private String url;

    public StreamingService getService() {
        return service;
    }

    public void setService(StreamingService service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        TrackDetails that = (TrackDetails) o;

        if (durationInMillis != that.durationInMillis) return false;
        if (service != that.service) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (interpret != null ? !interpret.equals(that.interpret) : that.interpret != null) return false;
        if (album != null ? !album.equals(that.album) : that.album != null) return false;
        if (coverUrl != null ? !coverUrl.equals(that.coverUrl) : that.coverUrl != null) return false;
        if (externalUrl != null ? !externalUrl.equals(that.externalUrl) : that.externalUrl != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = service != null ? service.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (int) (durationInMillis ^ (durationInMillis >>> 32));
        result = 31 * result + (interpret != null ? interpret.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (coverUrl != null ? coverUrl.hashCode() : 0);
        result = 31 * result + (externalUrl != null ? externalUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrackDetails{" +
                "service=" + service +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", durationInMillis=" + durationInMillis +
                ", interpret='" + interpret + '\'' +
                ", album='" + album + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", externalUrl='" + externalUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}