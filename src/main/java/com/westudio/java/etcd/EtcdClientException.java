package com.westudio.java.etcd;

import java.io.IOException;

public class EtcdClientException extends IOException {

    final Integer httpStatusCode;
    final EtcdResponse response;

    public EtcdClientException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = null;
        this.response = null;
    }

    public EtcdClientException(String message, int httpStatusCode) {
        super(message + "(" + httpStatusCode + ")");
        this.httpStatusCode = httpStatusCode;
        this.response = null;
    }

    public EtcdClientException(String message, EtcdResponse response) {
        super(message);
        this.httpStatusCode = null;
        this.response = response;
    }

    public boolean isHttpError() {
        return httpStatusCode != null;
    }

    public boolean isEtcdError() {
        return response != null;
    }
}
