package com.westudio.java.etcd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class EtcdCADTest {

    private EtcdClient client;

    @Before
    public void setUp() throws Exception {
        this.client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));
    }

    @Test
    public void testCAD() throws Exception {
        String key = "/cad";

        EtcdResponse result;

        result = this.client.set(key, "hello");
        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        Map<String, String> params = new HashMap<String, String>();
        params.put("prevValue", "world");
        try{
            this.client.cad(key, params);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
        }

        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);
        params.clear();
        params.put("prevValue", "hello");
        result = this.client.cad(key, params);
        Assert.assertEquals("compareAndDelete", result.action);
    }
}
