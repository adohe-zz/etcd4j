package com.xqbase.etcd4j;

import java.util.List;

public class EtcdNode {

    public String key;
    public long createdIndex;
    public long modifiedIndex;
    public String value;

    // For TTL keys
    public String expiration;
    public int ttl;

    // For listings
    public boolean dir;
    public List<EtcdNode> nodes;
}
