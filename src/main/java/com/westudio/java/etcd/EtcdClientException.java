package com.westudio.java.etcd;

import java.io.IOException;

public class EtcdClientException extends IOException {

    final Integer httpStatusCode;
    final EtcdErrorResponse errorResponse;

    public EtcdClientException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = null;
        this.errorResponse = null;
    }

    public EtcdClientException(String message, int httpStatusCode) {
        super(message + "(" + httpStatusCode + ")");
        this.httpStatusCode = httpStatusCode;
        this.errorResponse = null;
    }

    public EtcdClientException(String message, EtcdErrorResponse errorResponse) {
        super(message);
        this.httpStatusCode = null;
        this.errorResponse = errorResponse;
    }

    public boolean isHttpError() {
        return httpStatusCode != null;
    }

    public boolean isEtcdError() {
        return (errorResponse != null && errorResponse.errorCode != null);
    }
}
