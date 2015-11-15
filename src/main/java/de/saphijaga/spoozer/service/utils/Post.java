package de.saphijaga.spoozer.service.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static de.saphijaga.spoozer.service.utils.HttpUtils.*;

/**
 * Created by samuel on 13.11.15.
 */
public class Post {
    public static <T> T forObject(String url, Object param, Class<T> responseObjectClass) throws IOException {
        return forObject(url, param, new HashMap<>(), responseObjectClass);
    }

    public static <T> T forObject(String url, Map<String, String> params, Class<T> responseObjectClass) throws IOException {
        return forObject(url, params, new HashMap<>(), responseObjectClass);
    }

    public static <T> T forObject(String url, String body, Class<T> responseObjectClass) throws IOException {
        return forObject(url, body, new HashMap<>(), responseObjectClass);
    }

    public static <T> T forObject(String url, Object param, Map<String, String> header, Class<T> responseObjectClass) throws IOException {
        return forObject(url, objectToParamString(param), header, responseObjectClass);
    }

    public static <T> T forObject(String url, Map<String, String> params, Map<String, String> header, Class<T> responseObjectClass) throws IOException {
        return forObject(url, paramsToString(params), header, responseObjectClass);
    }

    public static <T> T forObject(String url, String body, Map<String, String> header, Class<T> responseObjectClass) throws IOException {
        return streamToObject(forStream(url, body, header), responseObjectClass);
    }

    public static String forString(String url, Object param) throws IOException {
        return forString(url, param, new HashMap<>());
    }

    public static String forString(String url, Map<String, String> params) throws IOException {
        return forString(url, params, new HashMap<>());
    }

    public static String forString(String url, String body) throws IOException {
        return streamToString(forStream(url, body, new HashMap<>()));
    }

    public static String forString(String url, Object param, Map<String, String> header) throws IOException {
        return forString(url, objectToParamString(param), header);
    }

    public static String forString(String url, Map<String, String> params, Map<String, String> header) throws IOException {
        return forString(url, paramsToString(params), header);
    }

    public static String forString(String url, String body, Map<String, String> header) throws IOException {
        return streamToString(forStream(url, body, header));
    }

    public static InputStream forStream(String url, Object param) throws IOException {
        return forStream(url, param, new HashMap<>());
    }

    public static InputStream forStream(String url, Map<String, String> params) throws IOException {
        return forStream(url, params, new HashMap<>());
    }

    public static InputStream forStream(String url, String body) throws IOException {
        return forStream(url, body, new HashMap<>());
    }

    public static InputStream forStream(String url, Map<String, String> params, Map<String, String> header) throws IOException {
        return forStream(url, paramsToString(params), header);
    }

    public static InputStream forStream(String url, Object param, Map<String, String> header) throws IOException {
        return forStream(url, objectToParamString(param), header);
    }

    public static InputStream forStream(String url, String body, Map<String, String> header) throws IOException {
        HttpURLConnection connection = buildConnection(url, "POST", header);
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(body);
        outputStream.flush();
        outputStream.close();
        return connection.getInputStream();
    }
}