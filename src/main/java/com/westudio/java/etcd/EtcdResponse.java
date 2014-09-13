package com.westudio.java.etcd;


public class EtcdResponse {

    // General
    public String action;
    public EtcdNode node;
    public EtcdNode prevNode;

    // Error
    public Integer errorCode;
    public String message;
    public String cause;
    public int index;

    public boolean isError() {
        return errorCode != null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
