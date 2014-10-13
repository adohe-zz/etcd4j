package com.westudio.java.etcd;

import com.google.gson.JsonParseException;
import org.apache.http.*;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EtcdClient implements Closeable {

    private static final String URI_PREFIX = "v2/keys";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int DEFAULT_CONNECT_TIMEOUT = 15 * 1000;

    // Future executor service
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final URI baseURI;
    private final CloseableHttpClient httpClient = buildClient();
    private final FutureRequestExecutionService futureExecutorService =
            new FutureRequestExecutionService(httpClient, executorService);

    private CloseableHttpClient buildClient() {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
            .setSocketTimeout(DEFAULT_CONNECT_TIMEOUT)
            .build();
        CloseableHttpClient httpClient;
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
            .setRedirectStrategy(new RedirectHandler())
            .setServiceUnavailableRetryStrategy(new RetryHandler())
            .build();
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
    public EtcdResponse get(String key) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, null);
        HttpGet httpGet;
        httpGet = new HttpGet(uri);

        return execute(httpGet);
    }

    /**
     * Changing the value of a key without ttl
     * @param key the key
     * @param value the value
     * @return response maybe a simple key node or a directory node
     */
    public EtcdResponse set(String key, String value) throws EtcdClientException {
        return set(key, value, null);
    }

    /**
     * Changing the value of a key with ttl setting
     * @param key the key
     * @param value the value
     * @param ttl the time-to-leave
     * @return response maybe a simple key node or a directory node
     */
    public EtcdResponse set(String key, String value, Integer ttl) throws EtcdClientException {
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        data.add(new BasicNameValuePair("value", value));
        if (ttl != null) {
            data.add(new BasicNameValuePair("ttl", String.valueOf(ttl)));
        }

        return put(key, data, null);
    }

    /**
     * Deleting a key
     * @param key the key
     * @return response
     */
    public EtcdResponse delete(String key) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, null);
        HttpDelete httpDelete = new HttpDelete(uri);

        return execute(httpDelete);
    }

    /**
     * Create a directory explicitly and you can't set the value
     * @param key the key
     * @param ttl the ttl setting
     * @param prevExist exist before
     * @return response
     * @throws EtcdClientException
     */
    public EtcdResponse createDir(String key, Integer ttl, Boolean prevExist) throws EtcdClientException {
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        data.add(new BasicNameValuePair("dir", String.valueOf(true)));
        if (ttl != null) {
            data.add(new BasicNameValuePair("ttl", String.valueOf(ttl)));
        }
        if (prevExist != null) {
            data.add(new BasicNameValuePair("prevExist", String.valueOf(prevExist)));
        }

        return put(key, data, null);
    }

    /**
     * Listing a directory
     * @param key the key
     * @param recursive listing directory recursive or not
     * @return response
     * @throws EtcdClientException
     */
    public List<EtcdNode> listDir(String key, Boolean recursive) throws EtcdClientException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("recursive", String.valueOf(recursive));

        URI uri = buildUriWithKeyAndParams(key, params);
        HttpGet httpGet = new HttpGet(uri);

        EtcdResponse response = execute(httpGet);
        if (response == null || response.node == null) {
            return null;
        }

        return response.node.nodes;
    }

    /**
     * Deleting a directory
     * @param key the key
     * @param recursive set recursive {@code true} if the directory holds keys
     * @return response
     */
    public EtcdResponse deleteDir(String key, Boolean recursive) throws EtcdClientException {
        Map<String, String> params = new HashMap<String, String>();
        if (recursive) {
            params.put("recursive", String.valueOf(true));
        } else {
            params.put("dir", String.valueOf(true));
        }

        URI uri = buildUriWithKeyAndParams(key, params);

        HttpDelete httpDelete = new HttpDelete(uri);
        return execute(httpDelete);
    }

    /**
     * Getting etcd version
     * @return the version
     * @throws IOException
     */
    public String getVersion() throws IOException {
        URI uri = baseURI.resolve("version");

        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new EtcdClientException("Error while geting version", httpResponse.getStatusLine().getStatusCode());
            } else {
                return EntityUtils.toString(httpResponse.getEntity(), DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            throw new EtcdClientException("Error while getting version", e);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
    }

    /**
     * List children under a key
     * @param key the key
     * @return response
     * @throws EtcdClientException
     */
    public EtcdResponse listChildren(String key) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key + "/", null);
        HttpGet httpGet = new HttpGet(uri);

        return execute(httpGet);
    }

    /**
     * Atomic compare and swap
     * @param key the key
     * @param value the new value
     * @param params comparable conditions
     * @return response
     * @throws EtcdClientException
     */
    public EtcdResponse cas(String key, String value, Map<String, String> params) throws EtcdClientException {
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        data.add(new BasicNameValuePair("value", value));

        return put(key, data, params);
    }

    /**
     * Atomic compare and delete
     * @param key the key
     * @param params comparable conditions
     * @return response
     * @throws EtcdClientException
     */
    public EtcdResponse cad(String key, Map<String, String> params) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, params);
        HttpDelete delete = new HttpDelete(uri);

        return execute(delete);
    }

    /**
     * Atomically create in-order keys
     * @param key the key for the container directory
     * @param value the value
     * @return response
     * @throws EtcdClientException
     */
    public EtcdResponse inOrderKeys(String key, String value) throws EtcdClientException {
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        data.add(new BasicNameValuePair("value", value));

        return post(key, data, null);
    }

    /**
     * Enumerate the in-order keys as a sorted list
     * @param key the directory key
     * @return a sorted list of in-order keys
     * @throws EtcdClientException
     */
    public List<EtcdNode> listInOrderKeys(String key) throws EtcdClientException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("recursive", String.valueOf(true));
        params.put("sorted", String.valueOf(true));

        URI uri = buildUriWithKeyAndParams(key, params);
        HttpGet httpGet = new HttpGet(uri);

        EtcdResponse response = execute(httpGet);

        if (response == null || response.node == null) {
            return null;
        }

        return response.node.nodes;
    }

    /**
     * Watch for a change on a key
     * @param key the key
     * @return a future task
     */
    public HttpRequestFutureTask<EtcdResponse> watch(String key) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("wait", String.valueOf(true));

        URI uri = buildUriWithKeyAndParams(key, params);
        HttpGet httpGet = new HttpGet(uri);

        return futureExecutorService.execute(httpGet, HttpClientContext.create(), new JsonResponseHandler());
    }

    /**
     * General put method
     * @param key the key
     * @param data put data
     * @param params url params
     * @return response
     * @throws EtcdClientException
     */
    private EtcdResponse put(String key, List<BasicNameValuePair> data, Map<String, String> params) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, params);
        HttpPut httpPut = new HttpPut(uri);

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(data, Charset.forName(DEFAULT_CHARSET));
        httpPut.setEntity(formEntity);

        return execute(httpPut);
    }

    /**
     * General post method
     * @param key the key
     * @param data post data
     * @param params url params
     * @return response
     * @throws EtcdClientException
     */
    private EtcdResponse post(String key, List<BasicNameValuePair> data, Map<String, String> params) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, params);
        HttpPost httpPost = new HttpPost(uri);

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(data, Charset.forName(DEFAULT_CHARSET));
        httpPost.setEntity(formEntity);

        return execute(httpPost);
    }

    /**
     * Execute the specific HttpUriRequest
     * @param request request instance
     * @return EtcdResponse
     * @throws EtcdClientException
     */
    private EtcdResponse execute(HttpUriRequest request) throws EtcdClientException {
        try {
            return httpClient.execute(request, new JsonResponseHandler());
        } catch (IOException e) {
            throw unwrap(e);
        }
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

    private EtcdClientException unwrap(IOException e) {
        if (e instanceof EtcdClientException) {
            return (EtcdClientException) e;
        } else {
            Throwable t = e.getCause();
            if (t instanceof EtcdClientException) {
                return (EtcdClientException) t;
            } else {
                return new EtcdClientException("Error execute request", e);
            }
        }
    }

    private static EtcdResponse parseResponse(String json) throws EtcdClientException {
        EtcdResponse response;
        try {
            response = Json.fromJson(json, EtcdResponse.class);
        } catch (JsonParseException e) {
            throw new EtcdClientException("Error parsing response", e);
        }

        return response;
    }

    private static EtcdErrorResponse parseErrorResponse(String json) throws EtcdClientException {
        EtcdErrorResponse response;
        try {
            response = Json.fromJson(json, EtcdErrorResponse.class);
        } catch (JsonParseException e) {
            throw new EtcdClientException("Error parsing response", e);
        }

        return response;
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }

    static class RedirectHandler implements RedirectStrategy {

        @Override
        public boolean isRedirected(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            return (statusCode >= 300 && statusCode < 400);
        }

        @Override
        public HttpUriRequest getRedirect(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
            String redirectUrl = httpResponse.getFirstHeader("location").getValue();
            HttpRequestWrapper requestWrapper = (HttpRequestWrapper) httpRequest;
            HttpUriRequest uriRequest;
            HttpRequest origin = requestWrapper.getOriginal();
            if (origin instanceof HttpPut) {
                uriRequest = new HttpPut(redirectUrl);
                ((HttpPut) uriRequest).setEntity(((HttpPut) origin).getEntity());
            } else if (origin instanceof HttpPost) {
                uriRequest = new HttpPost(redirectUrl);
                ((HttpPost) uriRequest).setEntity(((HttpPost) origin).getEntity());
            } else if (origin instanceof HttpDelete) {
                uriRequest = new HttpDelete(redirectUrl);
            } else {
                uriRequest = new HttpGet(redirectUrl);
            }

            return uriRequest;
        }
    }

    static class RetryHandler implements ServiceUnavailableRetryStrategy {

        @Override
        public boolean retryRequest(HttpResponse httpResponse, int i, HttpContext httpContext) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            // Retry 10 times
            return statusCode == 500 && i <= 10;
        }

        @Override
        public long getRetryInterval() {
            return 0;
        }
    }

    static class JsonResponseHandler implements ResponseHandler<EtcdResponse> {

        @Override
        public EtcdResponse handleResponse(HttpResponse httpResponse) throws IOException {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 500) {
                // Do if we still get 500 after 10 times retry
                throw new EtcdClientException("Error after 10 times", 500);
            }

            HttpEntity entity = httpResponse.getEntity();
            String json = null;
            if (entity != null) {
                try {
                    json = EntityUtils.toString(entity, DEFAULT_CHARSET);
                } catch (IOException e) {
                    throw new EtcdClientException("Error reading response", e);
                }
            }

            if (statusCode < 200 || statusCode >= 300) {
                EtcdErrorResponse response = parseErrorResponse(json);
                throw new EtcdClientException("Error response: " + response.message, response);
            }
            return parseResponse(json);
        }
    }
}
