package com.westudio.java.etcd;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EtcdClient {

    private static final CloseableHttpClient httpClient = buildClient();

    private static final String URI_PREFIX = "v2/keys";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;

    private final URI baseURI;

    private static CloseableHttpClient buildClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();
        CloseableHttpClient httpClient;
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    public EtcdClient(URI uri) {
        String url = uri.toString();
        if (!url.endsWith("/")) {
            url += "/";
            uri = URI.create(url);
        }
        this.baseURI = uri;
    }

    /**
     * Get the value of the key
     * @param key the key
     * @return the corresponding response
     */
    public EtcdResponse get(String key) {
        URI uri = buildUriWithKeyAndParams(key, null);
        HttpGet httpGet = new HttpGet(uri);

        EtcdResponse response = execute(httpGet);
        if (response.isError()) {
            if (response.errorCode == 100) {
                return null;
            }
        }

        return response;
    }

    /**
     * Changing the value of a key without ttl
     * @param key the key
     * @param value the value
     * @return response
     */
    public EtcdResponse set(String key, String value) throws EtcdClientException {
        return set(key, value, null);
    }

    /**
     * Changing the value of a key with ttl specific
     * @param key the key
     * @param value the value
     * @param ttl the time-to-leave
     * @return response
     */
    public EtcdResponse set(String key, String value, Integer ttl) throws EtcdClientException {
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        data.add(new BasicNameValuePair("value", value));
        if (ttl != null) {
            data.add(new BasicNameValuePair("ttl", String.valueOf(ttl)));
        }

        return put(key, data, null);
    }

    private EtcdResponse put(String key, List<BasicNameValuePair> data, Map<String, String> params) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, params);
        HttpPut httpPut = new HttpPut(uri);

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(data, Charset.forName(DEFAULT_CHARSET));
        httpPut.setEntity(formEntity);

        return execute(httpPut);
    }

    private EtcdResponse execute(HttpUriRequest request) {
        return null;
    }


    /**
     * Generate URI with key and params
     * @param key key of etcd
     * @param params the query params
     * @return A valid URI instance
     */
    private URI buildUriWithKeyAndParams(String key, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(URI_PREFIX);
        if (key.startsWith("/")) {
            key = key.substring(1);
        }
        for (String token : key.split("/")) {
            sb.append("/").append(urlEscape(token));
        }
        if (params != null) {
            sb.append("?");
            for (String str : params.keySet()) {
                sb.append(urlEscape(str)).append("=").append(urlEscape(params.get(str)));
                sb.append("&");
            }
        }

        String url = sb.toString();
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }
        return baseURI.resolve(url);
    }

    private String urlEscape(String url) {
        try {
            return URLEncoder.encode(url, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException();
        }
    }

    public static String format(Object obj) {
        try {
            return null;
        } catch (Exception e) {
            return "Error formatting response" + e.getMessage();
        }
    }
}
