package de.saphijaga.spoozer.service.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.springframework.web.util.UriUtils.encode;

/**
 * Created by samuel on 13.11.15.
 */
public class HttpUtils {
    public static <T> T streamToObject(InputStream inputStream, Class<T> streamObjectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, streamObjectClass);
    }

    public static String streamToString(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();
        if (response.length() > 0) {
            return response.substring(0, response.length() - 1);
        }
        return response.toString();
    }

    public static HttpURLConnection buildConnection(String url, String method, Map<String, String> header) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(method);
        header.forEach((key, value) -> connection.setRequestProperty(key, value));
        return connection;
    }

    public static String paramsToString(Map<String, String> params) {
        StringBuilder param = new StringBuilder();
        params.forEach((key, value) -> {
            try {
                param.append("&").append(encode(key, "utf8")).append("=").append(encode(value, "utf8"));
            } catch (UnsupportedEncodingException e) {
            }
        });
        if (param.length() > 0) {
            return param.substring(1);
        }
        return param.toString();
    }

    public static String objectToParamString(Object param) {
        Map<String, String> params = new HashMap<>();
        Class<?> pClass = param.getClass();
        List<Field> fields = new ArrayList<>();
        while (!pClass.equals(Object.class)) {
            fields.addAll(asList(pClass.getDeclaredFields()));
            pClass = pClass.getSuperclass();
        }
        fields.forEach(field -> {
            field.setAccessible(true);
            String key = field.getName();
            if (field.isAnnotationPresent(JsonProperty.class)) {
                key = field.getAnnotation(JsonProperty.class).value();
            }
            try {
                Object value = field.get(param);
                if (value != null) {
                    params.put(key, value.toString());
                }
            } catch (IllegalAccessException e) {
            }
        });
        return paramsToString(params);
    }
}