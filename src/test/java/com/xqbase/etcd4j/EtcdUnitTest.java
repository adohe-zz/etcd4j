package com.xqbase.etcd4j;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EtcdUnitTest {

    private String prefix;
    private EtcdClient client;

    @Before
    public void setUp() throws Exception {
        this.prefix = "/unittest-" + UUID.randomUUID().toString();
        this.client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));
    }

    @After
    public void tearDown() throws Exception {
        if (client == null) {
            return;
        }

        try {
            client.deleteDir(prefix, true);
        } catch (EtcdClientException e) {}
    }

    @Test
    public void testSetKeyAndGet() throws Exception {
        String key = prefix + "/message";

        this.client.set(key, "hello");

        String value = this.client.get(key);
        Assert.assertEquals("hello", value);

        this.client.set(key, "world");

        value = this.client.get(key);
        Assert.assertEquals("world", value);
    }

    @Test
    public void testGetNonExistKey() throws Exception {
        String key = prefix + "/doesnotexist";

        String value = this.client.get(key);
        Assert.assertNull(value);
    }

    @Test
    public void testDeleteKey() throws Exception {
        String key = prefix + "/testDelete";

        this.client.set(key, "hello");

        String value = this.client.get(key);
        Assert.assertEquals("hello", value);

        this.client.delete(key);

        value = this.client.get(key);
        Assert.assertNull(value);
    }

    @Test
    public void testDeleteNonExistKey() throws Exception {
        String key = prefix + "/doesnotexist";

        try {
			this.client.delete(key);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError(100));
        }
    }

    @Test
    public void testSetKeyWithTTL() throws Exception {
        String key = prefix + "/keyttl";

        this.client.set(key, "hello", 2);

        String value = this.client.get(key);
        Assert.assertEquals("hello", value);

        // TTL was redefined to mean TTL + 0.5s (Issue #306)
        Thread.sleep(3000);

        value = this.client.get(key);
        Assert.assertNull(value);
    }

    @Test
    public void testUpdateKeyTTL() throws Exception {
        String key = prefix + "/updatettl";

        this.client.set(key, "hello", 2);

        String value = this.client.get(key);
        Assert.assertEquals("hello", value);

        // TTL was redefined to mean TTL + 0.5s (Issue #306)
        Thread.sleep(1000);

        value = this.client.get(key);
        Assert.assertEquals("hello", value);

        this.client.set(key, "world", 2, true);

        Thread.sleep(2000);

        value = this.client.get(key);
        Assert.assertEquals("world", value);
    }

    @Test
    public void testCreateDirAndGet() throws Exception {
        String key = prefix + "/dir";

        this.client.createDir(key);

        List<EtcdNode> list = this.client.listDir(prefix);
        Assert.assertTrue(list.size() == 1);
        list = this.client.listDir(key);
        Assert.assertNull(list);
    }

    @Test
    public void testCreateDirWithTTL() throws Exception {
        String key = prefix + "/dirttl";

        this.client.createDir(key, 2);

        List<EtcdNode> list = this.client.listDir(prefix);

        Assert.assertTrue(list.size() == 1);

        Thread.sleep(3000);

        list = this.client.listDir(prefix);
        Assert.assertNull(list);
    }

    @Test
    public void testListDirNotExist() throws Exception {
        String key = prefix + "/listdirnotexist";

        List<EtcdNode> list = this.client.listDir(key);
        Assert.assertNull(list);
    }

    @Test
    public void testListDirWithoutRecursive() throws Exception {
        String key = prefix + "/dirnore";

        this.client.createDir(key);

        this.client.set(key + "/f1", "f1");
        this.client.set(key + "/f2", "f2");
        this.client.set(key + "/f3", "f3");
        this.client.set(key + "/subdir1/f", "f");

        List<EtcdNode> list = this.client.listDir(key);
        Assert.assertTrue(list.size() == 4);
        for (EtcdNode node : list) {
            if ("/subdir1".equals(node.key)) {
                Assert.assertNull(node.nodes);
            }
        }
    }

    @Test
    public void testListDirWithRecursive() throws Exception {
        String key = prefix + "/dirre";

        this.client.createDir(key);

        this.client.set(key + "/f1", "f1");
        this.client.set(key + "/f2", "f2");
        this.client.set(key + "/f3", "f3");
        this.client.set(key + "/subdir1/f", "f");

        List<EtcdNode> list = this.client.listDir(key, true);
        Assert.assertTrue(list.size() == 4);
        for (EtcdNode node : list) {
            if ("/subdir1".equals(node.key)) {
                Assert.assertTrue(node.nodes.size() == 1);
            }
        }
    }

    @Test
    public void testDeleteDir() throws Exception {
        String key = prefix + "/testDeleteDir";

        this.client.createDir(key);

        List<EtcdNode> list = this.client.listDir(prefix);
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).key.equals(key));

        this.client.deleteDir(key, false);

        list = this.client.listDir(prefix);
        Assert.assertNull(list);
    }

    @Test
    public void testDeleteDirNotExist() throws Exception {
        String key = prefix + "/testDeleteDirNotexist";

        try {
            this.client.deleteDir(key, false);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError(100));
        }
    }

    @Test
    public void testDeleteDirWithoutRecursive() throws Exception {
        String key = prefix + "/testDeleteDirwore";

        this.client.createDir(key);

        this.client.set(key + "/f1", "f1");
        List<EtcdNode> list = this.client.listDir(key);
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).key.equals(key + "/f1"));

        try {
            this.client.deleteDir(key, false);
            Assert.fail();
        } catch (EtcdClientException e) {
            Assert.assertTrue(e.isEtcdError(108));
        }

        list = this.client.listDir(key);
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).key.equals(key + "/f1"));
    }

    @Test
    public void testDeleteDirWithRecursive() throws Exception {
        String key = prefix + "/testDeleteDirre";

        this.client.createDir(key);

        this.client.set(key + "/f1", "f1");
        List<EtcdNode> list = this.client.listDir(key);
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).key.equals(key + "/f1"));

        this.client.deleteDir(key, true);

        list = this.client.listDir(key);
        Assert.assertNull(list);
    }

    @Test
    public void testCAS() throws Exception {
        String key = prefix + "/cas";

        EtcdResult result;

        this.client.set(key, "hello");
        String value = this.client.get(key);
        Assert.assertEquals("hello", value);

        Map<String, String> params = new HashMap<String, String>();
        params.put("prevExist", String.valueOf(false));
        result = this.client.cas(key, "world", params);
        Assert.assertEquals(true, result.isError());
        value = this.client.get(key);
        Assert.assertEquals("hello", value);

        params.clear();
        params.put("prevValue", "hello");
        result = this.client.cas(key, "world", params);
        Assert.assertEquals(false, result.isError());
        value = this.client.get(key);
        Assert.assertEquals("world", value);
    }

    @Test
    public void testCAD() throws Exception {
        String key = prefix + "/cad";

        EtcdResult result;

        this.client.set(key, "hello");
        String value = this.client.get(key);
        Assert.assertEquals("hello", value);

        Map<String, String> params = new HashMap<String, String>();
        params.put("prevValue", "world");
        result = this.client.cad(key, params);
        Assert.assertEquals(true, result.isError());
        value = this.client.get(key);
        Assert.assertEquals("hello", value);
    }
}
