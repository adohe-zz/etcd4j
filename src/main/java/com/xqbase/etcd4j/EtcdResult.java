package com.xqbase.etcd4j;

public class EtcdResult {

    // General values
    public String action;
    public EtcdNode node;
    public EtcdNode prevNode;

    // For errors
    public Integer errorCode;
    public String message;
    public String cause;
    public int index;

    public boolean isError() {
        return errorCode != null;
    }
}
