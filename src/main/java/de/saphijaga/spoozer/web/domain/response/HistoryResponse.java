package de.saphijaga.spoozer.web.domain.response;

import de.saphijaga.spoozer.web.details.HistoryDetails;

/**
 * Created by jan-ericgaidusch on 04.04.16.
 */
public class HistoryResponse {
    private HistoryDetails history;

    public HistoryResponse(HistoryDetails history){
        this.history = history;
    }

    public HistoryDetails getHistory() {
        return history;
    }

    public void setHistory(HistoryDetails history) {
        this.history = history;
    }
}
