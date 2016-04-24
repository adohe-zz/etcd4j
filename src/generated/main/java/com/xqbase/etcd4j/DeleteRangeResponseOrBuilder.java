// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: etcd.proto

package com.xqbase.etcd4j;

public interface DeleteRangeResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:etcd4j.DeleteRangeResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional .etcd4j.ResponseHeader header = 1;</code>
   */
  boolean hasHeader();
  /**
   * <code>optional .etcd4j.ResponseHeader header = 1;</code>
   */
  com.xqbase.etcd4j.ResponseHeader getHeader();
  /**
   * <code>optional .etcd4j.ResponseHeader header = 1;</code>
   */
  com.xqbase.etcd4j.ResponseHeaderOrBuilder getHeaderOrBuilder();

  /**
   * <code>optional int64 deleted = 2;</code>
   *
   * <pre>
   * Deleted is the number of keys that got deleted.
   * </pre>
   */
  long getDeleted();
}