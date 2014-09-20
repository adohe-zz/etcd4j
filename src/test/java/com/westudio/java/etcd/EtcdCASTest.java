package com.westudio.java.etcd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EtcdCASTest {

    private EtcdClient client;
    private String prefix;

    @Before
    public void setUp() throws Exception {
        client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));
        prefix = "/unittest-" + UUID.randomUUID().toString();
    }

    @Test
    public void testCAS() throws Exception {
        String key = prefix + "/cas";

        EtcdResponse result;

        result = this.client.set(key, "hello");
        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        Map<String, String> params = new HashMap<String, String>();
        params.put("prevExist", String.valueOf(false));
        result = this.client.cas(key, "world", params);
        Assert.assertEquals(true, result.isError());
        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        params.clear();
        params.put("prevValue", "hello");
        result = this.client.cas(key, "world", params);
        Assert.assertEquals(false, result.isError());
        result = this.client.get(key);
        Assert.assertEquals("world", result.node.value);
    }
}
