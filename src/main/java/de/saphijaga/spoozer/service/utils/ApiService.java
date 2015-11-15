package de.saphijaga.spoozer.service.utils;

import de.saphijaga.spoozer.service.Api;
import de.saphijaga.spoozer.service.StreamingService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samuel on 14.11.15.
 */
@Component
public class ApiService {
    private HashMap<StreamingService, Api> apis;

    public ApiService() {
        apis = new HashMap<>();
    }

    public void registerApi(Api api) {
        if (api.getService() == null || !api.getService().getApiClass().equals(api.getClass())) {
            throw new IllegalArgumentException("Api is wrong");
        }
        apis.put(api.getService(), api);
    }

    public <T extends Api> T getApi(StreamingService service) {
        return (T) apis.get(service);
    }

    public List<Api> getApis() {
        return new ArrayList<>(apis.values());
    }
}