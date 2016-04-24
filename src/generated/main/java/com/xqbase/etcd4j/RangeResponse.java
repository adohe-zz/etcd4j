// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: etcd.proto

package com.xqbase.etcd4j;

/**
 * Protobuf type {@code etcd4j.RangeResponse}
 */
public  final class RangeResponse extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:etcd4j.RangeResponse)
    RangeResponseOrBuilder {
  // Use RangeResponse.newBuilder() to construct.
  private RangeResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private RangeResponse() {
    kvs_ = java.util.Collections.emptyList();
    more_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private RangeResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            com.xqbase.etcd4j.ResponseHeader.Builder subBuilder = null;
            if (header_ != null) {
              subBuilder = header_.toBuilder();
            }
            header_ = input.readMessage(com.xqbase.etcd4j.ResponseHeader.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(header_);
              header_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              kvs_ = new java.util.ArrayList<com.xqbase.etcd4j.KeyValue>();
              mutable_bitField0_ |= 0x00000002;
            }
            kvs_.add(input.readMessage(com.xqbase.etcd4j.KeyValue.parser(), extensionRegistry));
            break;
          }
          case 24: {

            more_ = input.readBool();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw new RuntimeException(e.setUnfinishedMessage(this));
    } catch (java.io.IOException e) {
      throw new RuntimeException(
          new com.google.protobuf.InvalidProtocolBufferException(
              e.getMessage()).setUnfinishedMessage(this));
    } finally {
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        kvs_ = java.util.Collections.unmodifiableList(kvs_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.xqbase.etcd4j.EtcdJavaProto.internal_static_etcd4j_RangeResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.xqbase.etcd4j.EtcdJavaProto.internal_static_etcd4j_RangeResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.xqbase.etcd4j.RangeResponse.class, com.xqbase.etcd4j.RangeResponse.Builder.class);
  }

  private int bitField0_;
  public static final int HEADER_FIELD_NUMBER = 1;
  private com.xqbase.etcd4j.ResponseHeader header_;
  /**
   * <code>optional .etcd4j.ResponseHeader header = 1;</code>
   */
  public boolean hasHeader() {
    return header_ != null;
  }
  /**
   * <code>optional .etcd4j.ResponseHeader header = 1;</code>
   */
  public com.xqbase.etcd4j.ResponseHeader getHeader() {
    return header_ == null ? com.xqbase.etcd4j.ResponseHeader.getDefaultInstance() : header_;
  }
  /**
   * <code>optional .etcd4j.ResponseHeader header = 1;</code>
   */
  public com.xqbase.etcd4j.ResponseHeaderOrBuilder getHeaderOrBuilder() {
    return getHeader();
  }

  public static final int KVS_FIELD_NUMBER = 2;
  private java.util.List<com.xqbase.etcd4j.KeyValue> kvs_;
  /**
   * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
   */
  public java.util.List<com.xqbase.etcd4j.KeyValue> getKvsList() {
    return kvs_;
  }
  /**
   * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
   */
  public java.util.List<? extends com.xqbase.etcd4j.KeyValueOrBuilder> 
      getKvsOrBuilderList() {
    return kvs_;
  }
  /**
   * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
   */
  public int getKvsCount() {
    return kvs_.size();
  }
  /**
   * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
   */
  public com.xqbase.etcd4j.KeyValue getKvs(int index) {
    return kvs_.get(index);
  }
  /**
   * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
   */
  public com.xqbase.etcd4j.KeyValueOrBuilder getKvsOrBuilder(
      int index) {
    return kvs_.get(index);
  }

  public static final int MORE_FIELD_NUMBER = 3;
  private boolean more_;
  /**
   * <code>optional bool more = 3;</code>
   *
   * <pre>
   * more indicates if there are more keys to return in the requested range.
   * </pre>
   */
  public boolean getMore() {
    return more_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (header_ != null) {
      output.writeMessage(1, getHeader());
    }
    for (int i = 0; i < kvs_.size(); i++) {
      output.writeMessage(2, kvs_.get(i));
    }
    if (more_ != false) {
      output.writeBool(3, more_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (header_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getHeader());
    }
    for (int i = 0; i < kvs_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, kvs_.get(i));
    }
    if (more_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, more_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  public static com.xqbase.etcd4j.RangeResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }
  public static com.xqbase.etcd4j.RangeResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input);
  }
  public static com.xqbase.etcd4j.RangeResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input, extensionRegistry);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static com.xqbase.etcd4j.RangeResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.xqbase.etcd4j.RangeResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code etcd4j.RangeResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:etcd4j.RangeResponse)
      com.xqbase.etcd4j.RangeResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.xqbase.etcd4j.EtcdJavaProto.internal_static_etcd4j_RangeResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.xqbase.etcd4j.EtcdJavaProto.internal_static_etcd4j_RangeResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.xqbase.etcd4j.RangeResponse.class, com.xqbase.etcd4j.RangeResponse.Builder.class);
    }

    // Construct using com.xqbase.etcd4j.RangeResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        getKvsFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (headerBuilder_ == null) {
        header_ = null;
      } else {
        header_ = null;
        headerBuilder_ = null;
      }
      if (kvsBuilder_ == null) {
        kvs_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        kvsBuilder_.clear();
      }
      more_ = false;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.xqbase.etcd4j.EtcdJavaProto.internal_static_etcd4j_RangeResponse_descriptor;
    }

    public com.xqbase.etcd4j.RangeResponse getDefaultInstanceForType() {
      return com.xqbase.etcd4j.RangeResponse.getDefaultInstance();
    }

    public com.xqbase.etcd4j.RangeResponse build() {
      com.xqbase.etcd4j.RangeResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.xqbase.etcd4j.RangeResponse buildPartial() {
      com.xqbase.etcd4j.RangeResponse result = new com.xqbase.etcd4j.RangeResponse(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (headerBuilder_ == null) {
        result.header_ = header_;
      } else {
        result.header_ = headerBuilder_.build();
      }
      if (kvsBuilder_ == null) {
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          kvs_ = java.util.Collections.unmodifiableList(kvs_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.kvs_ = kvs_;
      } else {
        result.kvs_ = kvsBuilder_.build();
      }
      result.more_ = more_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.xqbase.etcd4j.RangeResponse) {
        return mergeFrom((com.xqbase.etcd4j.RangeResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.xqbase.etcd4j.RangeResponse other) {
      if (other == com.xqbase.etcd4j.RangeResponse.getDefaultInstance()) return this;
      if (other.hasHeader()) {
        mergeHeader(other.getHeader());
      }
      if (kvsBuilder_ == null) {
        if (!other.kvs_.isEmpty()) {
          if (kvs_.isEmpty()) {
            kvs_ = other.kvs_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureKvsIsMutable();
            kvs_.addAll(other.kvs_);
          }
          onChanged();
        }
      } else {
        if (!other.kvs_.isEmpty()) {
          if (kvsBuilder_.isEmpty()) {
            kvsBuilder_.dispose();
            kvsBuilder_ = null;
            kvs_ = other.kvs_;
            bitField0_ = (bitField0_ & ~0x00000002);
            kvsBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getKvsFieldBuilder() : null;
          } else {
            kvsBuilder_.addAllMessages(other.kvs_);
          }
        }
      }
      if (other.getMore() != false) {
        setMore(other.getMore());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.xqbase.etcd4j.RangeResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.xqbase.etcd4j.RangeResponse) e.getUnfinishedMessage();
        throw e;
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.xqbase.etcd4j.ResponseHeader header_ = null;
    private com.google.protobuf.SingleFieldBuilder<
        com.xqbase.etcd4j.ResponseHeader, com.xqbase.etcd4j.ResponseHeader.Builder, com.xqbase.etcd4j.ResponseHeaderOrBuilder> headerBuilder_;
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public boolean hasHeader() {
      return headerBuilder_ != null || header_ != null;
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public com.xqbase.etcd4j.ResponseHeader getHeader() {
      if (headerBuilder_ == null) {
        return header_ == null ? com.xqbase.etcd4j.ResponseHeader.getDefaultInstance() : header_;
      } else {
        return headerBuilder_.getMessage();
      }
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public Builder setHeader(com.xqbase.etcd4j.ResponseHeader value) {
      if (headerBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        header_ = value;
        onChanged();
      } else {
        headerBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public Builder setHeader(
        com.xqbase.etcd4j.ResponseHeader.Builder builderForValue) {
      if (headerBuilder_ == null) {
        header_ = builderForValue.build();
        onChanged();
      } else {
        headerBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public Builder mergeHeader(com.xqbase.etcd4j.ResponseHeader value) {
      if (headerBuilder_ == null) {
        if (header_ != null) {
          header_ =
            com.xqbase.etcd4j.ResponseHeader.newBuilder(header_).mergeFrom(value).buildPartial();
        } else {
          header_ = value;
        }
        onChanged();
      } else {
        headerBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public Builder clearHeader() {
      if (headerBuilder_ == null) {
        header_ = null;
        onChanged();
      } else {
        header_ = null;
        headerBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public com.xqbase.etcd4j.ResponseHeader.Builder getHeaderBuilder() {
      
      onChanged();
      return getHeaderFieldBuilder().getBuilder();
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    public com.xqbase.etcd4j.ResponseHeaderOrBuilder getHeaderOrBuilder() {
      if (headerBuilder_ != null) {
        return headerBuilder_.getMessageOrBuilder();
      } else {
        return header_ == null ?
            com.xqbase.etcd4j.ResponseHeader.getDefaultInstance() : header_;
      }
    }
    /**
     * <code>optional .etcd4j.ResponseHeader header = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        com.xqbase.etcd4j.ResponseHeader, com.xqbase.etcd4j.ResponseHeader.Builder, com.xqbase.etcd4j.ResponseHeaderOrBuilder> 
        getHeaderFieldBuilder() {
      if (headerBuilder_ == null) {
        headerBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            com.xqbase.etcd4j.ResponseHeader, com.xqbase.etcd4j.ResponseHeader.Builder, com.xqbase.etcd4j.ResponseHeaderOrBuilder>(
                getHeader(),
                getParentForChildren(),
                isClean());
        header_ = null;
      }
      return headerBuilder_;
    }

    private java.util.List<com.xqbase.etcd4j.KeyValue> kvs_ =
      java.util.Collections.emptyList();
    private void ensureKvsIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        kvs_ = new java.util.ArrayList<com.xqbase.etcd4j.KeyValue>(kvs_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        com.xqbase.etcd4j.KeyValue, com.xqbase.etcd4j.KeyValue.Builder, com.xqbase.etcd4j.KeyValueOrBuilder> kvsBuilder_;

    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public java.util.List<com.xqbase.etcd4j.KeyValue> getKvsList() {
      if (kvsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(kvs_);
      } else {
        return kvsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public int getKvsCount() {
      if (kvsBuilder_ == null) {
        return kvs_.size();
      } else {
        return kvsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public com.xqbase.etcd4j.KeyValue getKvs(int index) {
      if (kvsBuilder_ == null) {
        return kvs_.get(index);
      } else {
        return kvsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder setKvs(
        int index, com.xqbase.etcd4j.KeyValue value) {
      if (kvsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureKvsIsMutable();
        kvs_.set(index, value);
        onChanged();
      } else {
        kvsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder setKvs(
        int index, com.xqbase.etcd4j.KeyValue.Builder builderForValue) {
      if (kvsBuilder_ == null) {
        ensureKvsIsMutable();
        kvs_.set(index, builderForValue.build());
        onChanged();
      } else {
        kvsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder addKvs(com.xqbase.etcd4j.KeyValue value) {
      if (kvsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureKvsIsMutable();
        kvs_.add(value);
        onChanged();
      } else {
        kvsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder addKvs(
        int index, com.xqbase.etcd4j.KeyValue value) {
      if (kvsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureKvsIsMutable();
        kvs_.add(index, value);
        onChanged();
      } else {
        kvsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder addKvs(
        com.xqbase.etcd4j.KeyValue.Builder builderForValue) {
      if (kvsBuilder_ == null) {
        ensureKvsIsMutable();
        kvs_.add(builderForValue.build());
        onChanged();
      } else {
        kvsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder addKvs(
        int index, com.xqbase.etcd4j.KeyValue.Builder builderForValue) {
      if (kvsBuilder_ == null) {
        ensureKvsIsMutable();
        kvs_.add(index, builderForValue.build());
        onChanged();
      } else {
        kvsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder addAllKvs(
        java.lang.Iterable<? extends com.xqbase.etcd4j.KeyValue> values) {
      if (kvsBuilder_ == null) {
        ensureKvsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, kvs_);
        onChanged();
      } else {
        kvsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder clearKvs() {
      if (kvsBuilder_ == null) {
        kvs_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        kvsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public Builder removeKvs(int index) {
      if (kvsBuilder_ == null) {
        ensureKvsIsMutable();
        kvs_.remove(index);
        onChanged();
      } else {
        kvsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public com.xqbase.etcd4j.KeyValue.Builder getKvsBuilder(
        int index) {
      return getKvsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public com.xqbase.etcd4j.KeyValueOrBuilder getKvsOrBuilder(
        int index) {
      if (kvsBuilder_ == null) {
        return kvs_.get(index);  } else {
        return kvsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public java.util.List<? extends com.xqbase.etcd4j.KeyValueOrBuilder> 
         getKvsOrBuilderList() {
      if (kvsBuilder_ != null) {
        return kvsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(kvs_);
      }
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public com.xqbase.etcd4j.KeyValue.Builder addKvsBuilder() {
      return getKvsFieldBuilder().addBuilder(
          com.xqbase.etcd4j.KeyValue.getDefaultInstance());
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public com.xqbase.etcd4j.KeyValue.Builder addKvsBuilder(
        int index) {
      return getKvsFieldBuilder().addBuilder(
          index, com.xqbase.etcd4j.KeyValue.getDefaultInstance());
    }
    /**
     * <code>repeated .etcd4j.KeyValue kvs = 2;</code>
     */
    public java.util.List<com.xqbase.etcd4j.KeyValue.Builder> 
         getKvsBuilderList() {
      return getKvsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        com.xqbase.etcd4j.KeyValue, com.xqbase.etcd4j.KeyValue.Builder, com.xqbase.etcd4j.KeyValueOrBuilder> 
        getKvsFieldBuilder() {
      if (kvsBuilder_ == null) {
        kvsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            com.xqbase.etcd4j.KeyValue, com.xqbase.etcd4j.KeyValue.Builder, com.xqbase.etcd4j.KeyValueOrBuilder>(
                kvs_,
                ((bitField0_ & 0x00000002) == 0x00000002),
                getParentForChildren(),
                isClean());
        kvs_ = null;
      }
      return kvsBuilder_;
    }

    private boolean more_ ;
    /**
     * <code>optional bool more = 3;</code>
     *
     * <pre>
     * more indicates if there are more keys to return in the requested range.
     * </pre>
     */
    public boolean getMore() {
      return more_;
    }
    /**
     * <code>optional bool more = 3;</code>
     *
     * <pre>
     * more indicates if there are more keys to return in the requested range.
     * </pre>
     */
    public Builder setMore(boolean value) {
      
      more_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bool more = 3;</code>
     *
     * <pre>
     * more indicates if there are more keys to return in the requested range.
     * </pre>
     */
    public Builder clearMore() {
      
      more_ = false;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:etcd4j.RangeResponse)
  }

  // @@protoc_insertion_point(class_scope:etcd4j.RangeResponse)
  private static final com.xqbase.etcd4j.RangeResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.xqbase.etcd4j.RangeResponse();
  }

  public static com.xqbase.etcd4j.RangeResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RangeResponse>
      PARSER = new com.google.protobuf.AbstractParser<RangeResponse>() {
    public RangeResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      try {
        return new RangeResponse(input, extensionRegistry);
      } catch (RuntimeException e) {
        if (e.getCause() instanceof
            com.google.protobuf.InvalidProtocolBufferException) {
          throw (com.google.protobuf.InvalidProtocolBufferException)
              e.getCause();
        }
        throw e;
      }
    }
  };

  public static com.google.protobuf.Parser<RangeResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RangeResponse> getParserForType() {
    return PARSER;
  }

  public com.xqbase.etcd4j.RangeResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
