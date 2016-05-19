package de.saphijaga.spoozer.web.details;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jan-ericgaidusch on 04.04.16.
 */
public class HistoryDetails {
    private Map<Date, TrackDetails> history = new HashMap<>();

    public Map<Date, TrackDetails> getHistory() {
        return history;
    }

    public void setHistory(Map<Date, TrackDetails> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryDetails that = (HistoryDetails) o;

        return history != null ? history.equals(that.history) : that.history == null;

    }

    @Override
    public int hashCode() {
        return history != null ? history.hashCode() : 0;
    }
}