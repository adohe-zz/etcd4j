package com.westudio.java.etcd;

import java.io.IOException;

public class EtcdClientException extends IOException {

    final Integer httpStatusCode;

    public EtcdClientException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = null;
    }

    public EtcdClientException(String message, int httpStatusCode) {
        super(message + "(" + httpStatusCode + ")");
        this.httpStatusCode = httpStatusCode;
    }
}
