package com.westudio.java.etcd;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;

public class EtcdClientTest {

    private EtcdClient client;
    
    @Before
    public void setUp() throws Exception {
        client = new EtcdClient(new URI("http://127.0.0.1:8080"));
    }

    @Test
    public void testGet() throws Exception {
        EtcdResponse etcdResponse = client.get("test");
    }
}
