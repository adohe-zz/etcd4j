package com.westudio.java.etcd;

import org.hamcrest.MatcherAssert;
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
        long prevIndex = result.node.modifiedIndex;
        Assert.assertEquals("hello", result.node.value);

        Map<String, String> params = new HashMap<String, String>();
        params.put("prevValue", "not this");
        try{
            this.client.cad(key, params);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
            Assert.assertEquals(Integer.valueOf(101), e.errorResponse.errorCode);
            MatcherAssert.assertThat(e.errorResponse.message.toLowerCase(),
                                     containsString("compare failed"));
        }

        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        params.clear();
        params.put("prevIndex", Long.toString(prevIndex - 1));
        try{
            this.client.cad(key, params);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
            Assert.assertEquals(Integer.valueOf(101), e.errorResponse.errorCode);
            MatcherAssert.assertThat(e.errorResponse.message.toLowerCase(),
                                     containsString("compare failed"));
        }

        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        params.clear();
        params.put("prevValue", "hello");
        result = this.client.cad(key, params);
        Assert.assertEquals("compareAndDelete", result.action);
        Assert.assertEquals("hello", result.prevNode.value);
    }
}
