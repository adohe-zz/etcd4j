package com.xqbase.etcd4j;

public class CEtcdResult {

    // General values
    public String action;
    public CEtcdNode node;
    public CEtcdNode prevNode;

    // For errors
    public Integer errorCode;
    public String message;
    public String cause;
    public int index;

    public boolean isError() {
        return errorCode != null;
    }
}
