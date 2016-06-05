package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samuel on 05.04.16.
 */
@Document
public class Settings {
    @Id
    private String id;
    private Map<String, Object> list;

    public Settings() {
        this.list = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getList() {
        return list;
    }

    public void addList(Map<String, Object> properties) {
        properties.forEach((key, value) -> {
            if (value instanceof String && "[empty]".equals(value))
                this.list.remove(key);
            else
                this.list.put(key, value);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settings that = (Settings) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return list != null ? list.equals(that.list) : that.list == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
