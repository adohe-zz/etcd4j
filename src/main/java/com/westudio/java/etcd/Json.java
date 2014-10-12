package com.westudio.java.etcd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Json {

    private static final Gson gson = new GsonBuilder().create();

    private Json() {
      // no instantiation
    }

    public static String format(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            return "Error formatting response" + e.getMessage();
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
      return gson.fromJson(json, clazz);
    }
}
