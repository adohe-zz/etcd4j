package com.westudio.java.etcd;

import java.util.List;

public class EtcdNode {

    // General
    public String key;
    public String value;
    public long createdIndex;
    public long modifiedIndex;

    // TTL
    public String expiration;
    public Integer ttl;

    // Directory
    public boolean dir;
    public List<EtcdNode> nodes;

    @Override
    public String toString() {
        return super.toString();
    }
}
