package com.westudio.java.etcd;


public class EtcdErrorResponse {

    public Integer errorCode;
    public String message;
    public String cause;
    public int index;

    @Override
    public String toString() {
      return Json.format(this);
    }
}
