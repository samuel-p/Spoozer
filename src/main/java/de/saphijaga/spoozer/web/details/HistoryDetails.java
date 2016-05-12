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
}
