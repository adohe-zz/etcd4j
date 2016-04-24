package com.xqbase.etcd4j.v3.requests;

import com.google.common.util.concurrent.ListenableFuture;
import com.xqbase.etcd4j.v3.client.EtcdClientImpl;

/**
 * Basic etcd KV request.
 *
 * @author Tony He
 */
public class EtcdKeyRequest {

    // Required field for a valid etcd request.
    private final EtcdClientImpl client;
    private final String key;
    private final RequestMethod method;

    private String end;
    private int revision;
    private int limit;

    // for put
    private String value;

    public EtcdKeyRequest(EtcdClientImpl clientImpl, String key, RequestMethod method) {
        this.client = clientImpl;
        this.key = key;
        this.method = method;
    }

    public EtcdKeyRequest value(String value) {
        this.value = value;
        return this;
    }

    public EtcdKeyRequest end(String end) {
        this.end = end;
        return this;
    }

    public EtcdKeyRequest revision(int revision) {
        this.revision = revision;
        return this;
    }

    public EtcdKeyRequest limit(int limit) {
        this.limit = limit;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public RequestMethod getMethod() {
        return this.method;
    }

    public ListenableFuture<?> send() {
        return this.client.key(this);
    }
}
