package com.westudio.java.etcd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.UUID;

public class EtcdClientTest {

    private EtcdClient client;
    private String prefix;

    @Before
    public void setUp() throws Exception {
        client = new EtcdClient(new URI("http://etcd.soa.uat.qa.nt.ctripcorp.com"));
        prefix = "/unittest-" + UUID.randomUUID().toString();
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

        try {
            result = client.get(key);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
        }
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

        try {
            result = this.client.get(key);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError());
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
