package com.westudio.java.etcd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.List;

public class EtcdInOrderTest {

    private EtcdClient client;

    @Before
    public void setUp() throws Exception {
        this.client = new EtcdClient(URI.create("http://127.0.0.1:4001/"));
    }

    @Test
    public void testInOrderKeys() throws Exception {
        String key = "/queue";

        EtcdResponse result;

        result = this.client.deleteDir(key, true);
        Assert.assertEquals("delete", result.action);

        result = this.client.inOrderKeys(key, "Job1");
        Assert.assertEquals(result.node.value, "Job1");
        int k1 = Integer.valueOf(result.node.key.substring(result.node.key.lastIndexOf("/") + 1));
        result = this.client.inOrderKeys(key, "Job2");
        Assert.assertEquals(result.node.value, "Job2");
        int k2 = Integer.valueOf(result.node.key.substring(result.node.key.lastIndexOf("/") + 1));
        Assert.assertTrue(k2 > k1);

        List<EtcdNode> nodes = this.client.listInOrderKeys(key);
        Assert.assertTrue(nodes.size() == 2);
    }
}
