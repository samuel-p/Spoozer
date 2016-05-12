package de.saphijaga.spoozer.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by samuel on 13.11.15.
 */
public class Get {
    public static <T> T forObject(String url, Class<T> responseObjectClass) throws IOException {
        return forObject(url, new HashMap<>(), responseObjectClass);
    }

    public static <T> T forObject(String url, Map<String, String> header, Class<T> responseObjectClass) throws IOException {
        return HttpUtils.streamToObject(forStream(url, header), responseObjectClass);
    }

    public static String forString(String url) throws IOException {
        return forString(url, new HashMap<>());
    }

    public static String forString(String url, Map<String, String> header) throws IOException {
        return HttpUtils.streamToString(forStream(url, header));
    }

    public static InputStream forStream(String url) throws IOException {
        return forStream(url, new HashMap<>());
    }

    public static InputStream forStream(String url, Map<String, String> header) throws IOException {
        return HttpUtils.buildConnection(url, "GET", header).getInputStream();
    }

    private Get() {
    }
}