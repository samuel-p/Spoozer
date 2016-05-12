package de.saphijaga.spoozer.service.utils;

import de.saphijaga.spoozer.persistence.domain.Account;
import de.saphijaga.spoozer.service.AccountAccessDetails;
import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samuel on 14.11.15.
 */
@Component
public class ApiService {
    private Map<StreamingService, Api<? extends Account, ? extends AccountDetails, ? extends AccountAccessDetails>> apis;

    public ApiService() {
        apis = new EnumMap<>(StreamingService.class);
    }

    public void registerApi(Api<? extends Account, ? extends AccountDetails, ? extends AccountAccessDetails> api) {
        if (api.getService() == null || !api.getService().getApiClass().equals(api.getClass())) {
            throw new IllegalArgumentException("Api is wrong");
        }
        apis.put(api.getService(), api);
    }

    public <T extends Api<Account, AccountDetails, AccountAccessDetails>> T getApi(StreamingService service) {
        return (T) apis.get(service);
    }

    public List<Api<? extends Account, ? extends AccountDetails, ? extends AccountAccessDetails>> getApis() {
        return new ArrayList<>(apis.values());
    }
}