package com.xqbase.etcd4j;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;

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

        try {
            this.client.cas(key, "world", params);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
            Assert.assertEquals(Integer.valueOf(105), e.errorResponse.errorCode);
            MatcherAssert.assertThat(e.errorResponse.message.toLowerCase(),
                                     containsString("already exists"));
        }

        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        params.clear();
        params.put("prevValue", "not this");
        try {
            this.client.cas(key, "world", params);
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
        result = this.client.cas(key, "world", params);
        Assert.assertEquals("compareAndSwap", result.action);
        Assert.assertEquals("world", result.node.value);
        Assert.assertEquals("hello", result.prevNode.value);

        result = this.client.get(key);
        Assert.assertEquals("world", result.node.value);
    }
}
