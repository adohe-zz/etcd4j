package com.xqbase.etcd4j;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * An async ETCD Java Client.
 *
 * @author Tony He
 */
public class EtcdClient implements Closeable {

    private static final CloseableHttpAsyncClient httpClient = buildHttpClient();
    private static final Gson gson = new GsonBuilder().create();

    private static final String URI_PREFIX = "v2/keys";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String RETRY_COUNTER_HEADER = "retry-count";

    // Timeout
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;
    private static final int FUTURE_TIMEOUT = 20 * 1000;
    private static final int MAX_RETRY_COUNT = 5;

    private final URI baseUri;

    private final ExecutorService delegateAsyncTransformer = Executors.newCachedThreadPool(new DaemonThreadFactory());
    private final ListeningExecutorService transformerPool = MoreExecutors.listeningDecorator(delegateAsyncTransformer);

    private static CloseableHttpAsyncClient buildHttpClient() {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .build();

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        // Start
        httpAsyncClient.start();
        return httpAsyncClient;
    }

    public EtcdClient(URI uri) {
        String url = uri.toString();
        if (!url.endsWith("/")) {
            url += "/";
            uri = URI.create(url);
        }
        this.baseUri = uri;
    }

    /**
     * Get the value of a key.
     * @param key the key
     * @return the corresponding value
     */
    public String get(String key) throws EtcdClientException {
        Preconditions.checkNotNull(key, "key can't be null");

        URI uri = buildUriWithKeyAndParams(key, null);
        HttpGet httpGet = new HttpGet(uri);

        EtcdResult result = syncExecute(httpGet, new int[] {200, 404}, 100);
        if (result.isError()) {
            if (result.errorCode == 100) {
                return null;
            }
        }

        return result.node != null ? result.node.value : null;
    }

    /**
     * Setting the value of a key
     * @param key the key
     * @param value the value
     */
    public void set(String key, String value) throws EtcdClientException {
        set(key, value, 0);
    }

    /**
     * Setting the value of a key with optional key TTL
     * @param key the key
     * @param value the value
     * @param ttl optional key TTL
     */
    public void set(String key, String value, int ttl) throws EtcdClientException {
        set(key, value, ttl, null);
    }

    /**
     * Setting the value of a key with ttl and optional prevExist(update key ttl)
     * @param key the key
     * @param value the value
     * @param ttl the ttl
     * @param prevExist exists before
     */
    public void set(String key, String value, int ttl, Boolean prevExist) throws EtcdClientException {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        List<BasicNameValuePair> list = Lists.newArrayList();
        list.add(new BasicNameValuePair("value", value));
        if (ttl > 0) {
            list.add(new BasicNameValuePair("ttl", String.valueOf(ttl)));
        }
        if (prevExist != null) {
            list.add(new BasicNameValuePair("prevExist", String.valueOf(prevExist)));
        }

        put(key, list, null, new int[]{200, 201});
    }

    /**
     * Delete a key
     * @param key the key
     * // @return operation result
     */
    public void delete(String key) throws EtcdClientException {
        Preconditions.checkNotNull(key);

        URI uri = buildUriWithKeyAndParams(key, null);
        HttpDelete delete = new HttpDelete(uri);

        syncExecute(delete, new int[]{200, 404});
    }

    /**
     * Creating directories
     * @param key the dir key
     * @throws EtcdClientException
     */
    public void createDir(String key) throws EtcdClientException {
        createDir(key, 0);
    }

    /**
     * Create directories with optional ttl
     * @param key the key
     * @param ttl the ttl
     * @throws EtcdClientException
     */
    public void createDir(String key, int ttl) throws EtcdClientException {
        createDir(key, ttl, null);
    }

    /**
     * Create directories with optional ttl and prevExist(update dir ttl)
     * @param key the key
     * @param ttl the ttl
     * @param prevExist exists before
     * @throws EtcdClientException
     */
    public void createDir(String key, int ttl, Boolean prevExist) throws EtcdClientException {
        Preconditions.checkNotNull(key);

        List<BasicNameValuePair> data = Lists.newArrayList();
        data.add(new BasicNameValuePair("dir", String.valueOf(true)));
        if (ttl > 0) {
            data.add(new BasicNameValuePair("ttl", String.valueOf(ttl)));
        }
        if (prevExist != null) {
            data.add(new BasicNameValuePair("prevExist", String.valueOf(prevExist)));
        }

        put(key, data, null, new int[]{200, 201});
    }

    /**
     * Listing a directory
     * @param key the dir key
     * @return a CEtcdNode list
     * @throws EtcdClientException
     */
    public List<EtcdNode> listDir(String key) throws EtcdClientException {
        return listDir(key, false);
    }

    /**
     * Listing a directory with recursive
     * @param key the dir key
     * @param recursive recursive
     * @return CEtcdNode list
     * @throws EtcdClientException
     */
    public List<EtcdNode> listDir(String key, boolean recursive) throws EtcdClientException {
        Preconditions.checkNotNull(key);

        Map<String, String> params = new HashMap<String, String>();
        if (recursive) {
            params.put("recursive", String.valueOf(true));
        }

        URI uri = buildUriWithKeyAndParams(key, params);
        HttpGet httpGet = new HttpGet(uri);

        EtcdResult result = syncExecute(httpGet, new int[] {200, 404}, 100);
        if (null == result || null == result.node) {
            return null;
        }

        return result.node.nodes;
    }

    /**
     * Deleting a directory
     * @param key the dir key
     * @param recursive set recursive=true if the directory holds keys
     * // @return operation result
     * @throws EtcdClientException
     */
    public void deleteDir(String key, boolean recursive) throws EtcdClientException {
        Preconditions.checkNotNull(key);

        Map<String, String> params = new HashMap<String, String>();
        if (recursive) {
            params.put("recursive", String.valueOf(true));
        } else {
            params.put("dir", String.valueOf(true));
        }

        URI uri = buildUriWithKeyAndParams(key, params);

        HttpDelete httpDelete = new HttpDelete(uri);
        syncExecute(httpDelete, new int[]{200, 403});
    }

    /**
     * Atomic Compare-and-Swap
     * @param key the key
     * @param value the new value
     * @param params comparable conditions
     * @return operation result
     * @throws EtcdClientException
     */
    public EtcdResult cas(String key, String value, Map<String, String> params) throws EtcdClientException {
        List<BasicNameValuePair> data = Lists.newArrayList();
        data.add(new BasicNameValuePair("value", value));

        return put(key, data, params, new int[] {200, 412}, 101, 105);
    }

    /**
     * Atomic Compare-and-Delete
     * @param key the key
     * @param params comparable conditions
     * @return operation result
     * @throws EtcdClientException
     */
    public EtcdResult cad(String key, Map<String, String> params) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, params);
        HttpDelete httpDelete = new HttpDelete(uri);

        return syncExecute(httpDelete, new int[] {200, 412}, 101);
    }

    /**
     * Watch for a change on a key
     * @param key the key
     * @return a future result
     */
    public ListenableFuture<EtcdResult> watch(String key) {
        return watch(key, 0L, false);
    }

    /**
     * Watch for a change on a key
     * @param key the key
     * @param index the wait index
     * @param recursive set recursive true if you want to watch for child keys
     * @return a future result
     */
    public ListenableFuture<EtcdResult> watch(String key, long index, boolean recursive) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("wait", String.valueOf(true));
        if (index > 0) {
            params.put("waitIndex", String.valueOf(index));
        }
        if (recursive) {
            params.put("recursive", String.valueOf(recursive));
        }

        URI uri = buildUriWithKeyAndParams(key, params);
        HttpGet httpGet = new HttpGet(uri);

        return asyncExecute(httpGet, new int[] {200});
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    // The basic put operation
    private EtcdResult put(String key, List<BasicNameValuePair> data, Map<String, String> params, int[] expectedHttpStatusCodes,
                int... expectedErrorCodes) throws EtcdClientException {
        URI uri = buildUriWithKeyAndParams(key, params);
        HttpPut httpPut = new HttpPut(uri);

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data, Charsets.UTF_8);
        httpPut.setEntity(entity);

        return syncExecute(httpPut, expectedHttpStatusCodes, expectedErrorCodes);
    }

    private EtcdResult syncExecute(HttpUriRequest request, int[] expectedHttpStatusCodes, final int... exceptedErrorCodes) throws EtcdClientException {
        try {
            return asyncExecute(request, expectedHttpStatusCodes, exceptedErrorCodes).get(FUTURE_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new EtcdClientException("InterruptedException", e);
        } catch (TimeoutException e) {
            throw new EtcdClientException("TimeoutException", e);
        } catch (ExecutionException e) {
            throw unwrap(e);
        }
    }

    private ListenableFuture<EtcdResult> asyncExecute(final HttpUriRequest request, final int[] expectedHttpStatusCodes, final int... expectedErrorCodes) {
        ListenableFuture<HttpResponse> response = asyncExecuteHttp(request);
        return Futures.transform(response, new AsyncTransformation(request, transformerPool, expectedHttpStatusCodes, expectedErrorCodes));
    }

    private ListenableFuture<HttpResponse> asyncExecuteHttp(final HttpUriRequest request) {
        final SettableFuture<HttpResponse> future = SettableFuture.create();

        httpClient.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                future.set(httpResponse);
            }

            @Override
            public void failed(Exception e) {
                future.setException(e);
            }

            @Override
            public void cancelled() {
                future.setException(new InterruptedException());
            }
        });

        return future;
    }

    private EtcdClientException unwrap(ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof EtcdClientException) {
            return (EtcdClientException)cause;
        } else {
            return new EtcdClientException("Failed to execute request", e);
        }
    }

    private class AsyncTransformation implements AsyncFunction<HttpResponse, EtcdResult> {

        private final ListeningExecutorService transformPool;
        private final HttpUriRequest request;
        private final int[] expectedHttpStatusCodes;
        private final int[] exceptedErrorCodes;

        AsyncTransformation(HttpUriRequest request, ListeningExecutorService transformPool, int[] expectedHttpStatusCodes, int... expectedErrorCodes) {
            this.request = request;
            this.transformPool = transformPool;
            this.expectedHttpStatusCodes = expectedHttpStatusCodes;
            this.exceptedErrorCodes = expectedErrorCodes;
        }

        @Override
        public ListenableFuture<EtcdResult> apply(HttpResponse response) throws Exception {
            if (response.getStatusLine().getStatusCode() == 500) {
                Header retryHeader = request.getFirstHeader(RETRY_COUNTER_HEADER);
                if (retryHeader != null) {
                    Integer count = Integer.parseInt(retryHeader.getValue());
                    if (count > 0) {
                        request.removeHeader(retryHeader);
                        request.addHeader(RETRY_COUNTER_HEADER, String.valueOf(--count));
                        return asyncExecute(request, expectedHttpStatusCodes);
                    }
                } else {
                    request.addHeader(RETRY_COUNTER_HEADER, String.valueOf(MAX_RETRY_COUNT));
                    return asyncExecute(request, expectedHttpStatusCodes);
                }
            }

            return transformPool.submit(new TransformerWorker(response, expectedHttpStatusCodes, exceptedErrorCodes));
        }


        private class TransformerWorker implements Callable<EtcdResult> {

            private final HttpResponse response;
            private final int[] expectedHttpStatusCodes;
            private final int[] expectedErrorCodes;

            private TransformerWorker(HttpResponse response, int[] expectedHttpStatusCodes, int... expectedErrorCodes) {
                this.response = response;
                this.expectedHttpStatusCodes = expectedHttpStatusCodes;
                this.expectedErrorCodes = expectedErrorCodes;
            }

            @Override
            public EtcdResult call() throws Exception {
                try {
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();

                    String json = null;
                    if (response.getEntity() != null) {
                        try {
                            json = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
                        } catch (IOException e) {
                            throw new EtcdClientException("Error reading response", e);
                        }
                    }

                    if (!contains(expectedHttpStatusCodes, statusCode)) {
                        if (statusCode == 404 && json != null) {
                            // More info in json
                        } else {
                            throw new EtcdClientException("Error response from etcd: " + statusLine.getReasonPhrase(),
                                    statusCode);
                        }
                    }

                    if (json != null) {
                        EtcdResult result = parseCEtcdResult(json);

                        if (result.isError()) {
                            if (!contains(expectedErrorCodes, result.errorCode)) {
                                throw new EtcdClientException(result.message, result);
                            }
                        }

                        return result;
                    }
                } finally {
                    close(response);
                }

                return null;
            }
        }
    }

    private static class DaemonThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    }

    private EtcdResult parseCEtcdResult(String json) throws EtcdClientException {
        EtcdResult result;
        try {
            result = gson.fromJson(json, EtcdResult.class);
        } catch (JsonParseException e) {
            throw new EtcdClientException("Error parsing response", e);
        }

        return result;
    }

    private static void close(HttpResponse response) {
        if (response == null) {
            return;
        }

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            EntityUtils.consumeQuietly(entity);
        }
    }

    private static boolean contains(int[] exceptedCodes, int code) {
        for (int exceptedCode : exceptedCodes) {
            if (exceptedCode == code) {
                return true;
            }
        }

        return false;
    }

    // Build url with key and url params
    private URI buildUriWithKeyAndParams(String key, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(URI_PREFIX);
        if (key.startsWith("/")) {
            key = key.substring(1);
        }
        for (String token : Splitter.on("/").split(key)) {
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
        return baseUri.resolve(url);
    }

    private static String urlEscape(String url) {
        try {
            return URLEncoder.encode(url, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException();
        }
    }
}
