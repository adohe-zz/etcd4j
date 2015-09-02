package com.xqbase.etcd4j;

import java.io.IOException;

public class EtcdClientException extends IOException {

    private static final long serialVersionUID = 1L;

    private EtcdResult result;

    public EtcdClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public EtcdClientException(String message, int httpStatusCode) {
        super(message + "(" + httpStatusCode + ")");
    }

    public EtcdClientException(String message, EtcdResult result) {
        super(message);
        this.result = result;
    }

    public boolean isEtcdError(int etcdCode) {
        return (this.result != null && etcdCode == this.result.errorCode);

    }
}
