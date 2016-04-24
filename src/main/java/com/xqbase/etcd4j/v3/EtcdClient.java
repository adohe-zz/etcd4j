package com.xqbase.etcd4j.v3;

import com.xqbase.etcd4j.v3.client.EtcdClientImpl;
import com.xqbase.etcd4j.v3.requests.EtcdKeyRequest;
import com.xqbase.etcd4j.v3.requests.RequestMethod;

/**
 * Java client for etcd v3.
 */
public class EtcdClient {

    private final EtcdClientImpl client;

    /**
     * Construct client for accessing etcd server at {@code host:port}.
     * @param host
     * @param port
     */
    public EtcdClient(String host, int port) {
        this(new EtcdClientImpl(host, port));
    }

    /**
     * Construct a client for accessing etcd server using the
     * existing EtcdClientImpl.
     *
     * @param etcdClientImpl
     */
    public EtcdClient(EtcdClientImpl etcdClientImpl) {
        this.client = etcdClientImpl;
    }

    // get retrieves keys.
    // By default, Get will return the value for "key", if any.
    public EtcdKeyRequest get(String key) {
        return new EtcdKeyRequest(this.client, key, RequestMethod.HttpGet);
    }

    // put puts a key-value pair into etcd.
    // Note that key,value can be plain bytes array and string is
    // an immutable representation of that bytes array.
    public EtcdKeyRequest put(String key, String value) {
        return new EtcdKeyRequest(this.client, key, RequestMethod.HttpPut).value(value);
    }
}
