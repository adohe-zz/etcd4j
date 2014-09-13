package com.westudio.java.etcd;

import java.io.IOException;

public class EtcdClientException extends IOException {

    public EtcdClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
