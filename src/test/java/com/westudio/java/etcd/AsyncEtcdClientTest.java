package com.westudio.java.etcd;

import com.google.common.util.concurrent.ListenableFuture;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncEtcdClientTest {

    String prefix;
    AsyncEtcdClient client;

    @Before
    public void setUp() throws Exception {
        this.prefix = "/unittest-" + UUID.randomUUID().toString();
    }

    @After
    public void tearDown() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void testSetAndGet() throws Exception {
        String key = prefix + "/message";

        EtcdResponse result;

        result = this.client.set(key, "hello");
        Assert.assertEquals("set", result.action);
        Assert.assertEquals("hello", result.node.value);
        Assert.assertNull(result.prevNode);

        result = this.client.get(key);
        Assert.assertEquals("get", result.action);
        Assert.assertEquals("hello", result.node.value);
        Assert.assertNull(result.prevNode);

        result = this.client.set(key, "world");
        Assert.assertEquals("set", result.action);
        Assert.assertEquals("world", result.node.value);
        Assert.assertNotNull(result.prevNode);
        Assert.assertEquals("hello", result.prevNode.value);

        result = this.client.get(key);
        Assert.assertEquals("get", result.action);
        Assert.assertEquals("world", result.node.value);
        Assert.assertNull(result.prevNode);
    }

    @Test
    public void testGetNonExistKey() throws Exception {
        String key = prefix + "/doesnotexist";

        EtcdResponse result;

        result = this.client.get(key);
        Assert.assertNull(result);
    }

    @Test
    public void testDelete() throws Exception {
        String key = prefix + "/testDelete";

        EtcdResponse result;

        result = this.client.set(key, "hello");

        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        result = this.client.delete(key);
        Assert.assertEquals("delete", result.action);
        Assert.assertEquals(null, result.node.value);
        Assert.assertNotNull(result.prevNode);
        Assert.assertEquals("hello", result.prevNode.value);

        result = this.client.get(key);
        Assert.assertNull(result);
    }

    @Test
    public void testDeleteNonExistKey() throws Exception {
        String key = prefix + "/doesnotexist";

        try {
            this.client.delete(key);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
        }
    }

    @Test
    public void testSetKeyWithTTL() throws Exception {
        String key = prefix + "/ttl";

        EtcdResponse result;

        result = this.client.set(key, "hello", 2);
        Assert.assertNotNull(result.node.expiration);
        Assert.assertTrue(result.node.ttl == 2 || result.node.ttl == 1);

        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        // TTL was redefined to mean TTL + 0.5s (Issue #306)
        Thread.sleep(3000);

        result = this.client.get(key);
        Assert.assertNull(result);
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

    @Test
    public void testCAD() throws Exception {
        String key = prefix + "/cad";

        EtcdResponse result;

        result = this.client.set(key, "hello");
        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);

        Map<String, String> params = new HashMap<String, String>();
        params.put("prevValue", "world");
        result = this.client.cad(key, params);
        Assert.assertEquals(true, result.isError());
        result = this.client.get(key);
        Assert.assertEquals("hello", result.node.value);
    }

    @Test
    public void testWatchPrefix() throws Exception {
        String key = prefix + "/watch";

        EtcdResponse result = this.client.set(key + "/f2", "f2");
        Assert.assertTrue(!result.isError());
        Assert.assertNotNull(result.node);
        Assert.assertEquals("f2", result.node.value);

        ListenableFuture<EtcdResponse> watchFuture = this.client.watch(key,
                result.node.modifiedIndex + 1,
                true);
        try {
            EtcdResponse watchResult = watchFuture
                    .get(100, TimeUnit.MILLISECONDS);
            Assert.fail("Subtree watch fired unexpectedly: " + watchResult);
        } catch (TimeoutException e) {
            // Expected
        }

        Assert.assertFalse(watchFuture.isDone());

        result = this.client.set(key + "/f1", "f1");
        Assert.assertTrue(!result.isError());
        Assert.assertNotNull(result.node);
        Assert.assertEquals("f1", result.node.value);

        EtcdResponse watchResult = watchFuture.get(100, TimeUnit.MILLISECONDS);

        Assert.assertNotNull(watchResult);
        Assert.assertTrue(!watchResult.isError());
        Assert.assertNotNull(watchResult.node);

        {
            Assert.assertEquals(key + "/f1", watchResult.node.key);
            Assert.assertEquals("f1", watchResult.node.value);
            Assert.assertEquals("set", watchResult.action);
            Assert.assertNull(result.prevNode);
            Assert.assertEquals(result.node.modifiedIndex,
                    watchResult.node.modifiedIndex);
        }
    }

    @Test
    public void testList() throws Exception {
        String key = prefix + "/dir";

        EtcdResponse result;

        result = this.client.set(key + "/f1", "f1");
        Assert.assertEquals("f1", result.node.value);
        result = this.client.set(key + "/f2", "f2");
        Assert.assertEquals("f2", result.node.value);
        result = this.client.set(key + "/f3", "f3");
        Assert.assertEquals("f3", result.node.value);
        result = this.client.set(key + "/subdir1/f", "f");
        Assert.assertEquals("f", result.node.value);

        EtcdResponse listing = this.client.listChildren(key);
        Assert.assertEquals(4, listing.node.nodes.size());
        Assert.assertEquals("get", listing.action);
    }

    @Test
    public void testGetVersion() throws Exception {
        String version = this.client.getVersion();
        Assert.assertTrue(version.startsWith("etcd 0."));
    }
}
