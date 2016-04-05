package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samuel on 05.04.16.
 */
@Document
public class Properties {
    @Id
    private String id;
    private Map<String, Object> properties;

    public Properties() {
        this.properties = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void addProperties(Map<String, Object> properties) {
        properties.forEach((key, value) -> {
            if (value instanceof String && "[empty]".equals(value))
                this.properties.remove(key);
            else
                this.properties.put(key, value);
        });
    }
}
