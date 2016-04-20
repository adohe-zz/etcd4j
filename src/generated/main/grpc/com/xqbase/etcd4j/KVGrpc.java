package com.xqbase.etcd4j;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class KVGrpc {

  private KVGrpc() {}

  public static final String SERVICE_NAME = "etcd4j.KV";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.RangeRequest,
      com.xqbase.etcd4j.RangeResponse> METHOD_RANGE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.KV", "Range"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.RangeRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.RangeResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.PutRequest,
      com.xqbase.etcd4j.PutResponse> METHOD_PUT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.KV", "Put"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.PutRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.PutResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.DeleteRangeRequest,
      com.xqbase.etcd4j.DeleteRangeResponse> METHOD_DELETE_RANGE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.KV", "DeleteRange"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.DeleteRangeRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.DeleteRangeResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.TxnRequest,
      com.xqbase.etcd4j.TxnResponse> METHOD_TXN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.KV", "Txn"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.TxnRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.TxnResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.CompactionRequest,
      com.xqbase.etcd4j.CompactionResponse> METHOD_COMPACT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.KV", "Compact"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.CompactionRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.CompactionResponse.getDefaultInstance()));

  public static KVStub newStub(io.grpc.Channel channel) {
    return new KVStub(channel);
  }

  public static KVBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new KVBlockingStub(channel);
  }

  public static KVFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new KVFutureStub(channel);
  }

  public static interface KV {

    public void range(com.xqbase.etcd4j.RangeRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.RangeResponse> responseObserver);

    public void put(com.xqbase.etcd4j.PutRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.PutResponse> responseObserver);

    public void deleteRange(com.xqbase.etcd4j.DeleteRangeRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.DeleteRangeResponse> responseObserver);

    public void txn(com.xqbase.etcd4j.TxnRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.TxnResponse> responseObserver);

    public void compact(com.xqbase.etcd4j.CompactionRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.CompactionResponse> responseObserver);
  }

  public static interface KVBlockingClient {

    public com.xqbase.etcd4j.RangeResponse range(com.xqbase.etcd4j.RangeRequest request);

    public com.xqbase.etcd4j.PutResponse put(com.xqbase.etcd4j.PutRequest request);

    public com.xqbase.etcd4j.DeleteRangeResponse deleteRange(com.xqbase.etcd4j.DeleteRangeRequest request);

    public com.xqbase.etcd4j.TxnResponse txn(com.xqbase.etcd4j.TxnRequest request);

    public com.xqbase.etcd4j.CompactionResponse compact(com.xqbase.etcd4j.CompactionRequest request);
  }

  public static interface KVFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.RangeResponse> range(
        com.xqbase.etcd4j.RangeRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.PutResponse> put(
        com.xqbase.etcd4j.PutRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.DeleteRangeResponse> deleteRange(
        com.xqbase.etcd4j.DeleteRangeRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.TxnResponse> txn(
        com.xqbase.etcd4j.TxnRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.CompactionResponse> compact(
        com.xqbase.etcd4j.CompactionRequest request);
  }

  public static class KVStub extends io.grpc.stub.AbstractStub<KVStub>
      implements KV {
    private KVStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KVStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KVStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KVStub(channel, callOptions);
    }

    @java.lang.Override
    public void range(com.xqbase.etcd4j.RangeRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.RangeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RANGE, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void put(com.xqbase.etcd4j.PutRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.PutResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PUT, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void deleteRange(com.xqbase.etcd4j.DeleteRangeRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.DeleteRangeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_RANGE, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void txn(com.xqbase.etcd4j.TxnRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.TxnResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TXN, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void compact(com.xqbase.etcd4j.CompactionRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.CompactionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COMPACT, getCallOptions()), request, responseObserver);
    }
  }

  public static class KVBlockingStub extends io.grpc.stub.AbstractStub<KVBlockingStub>
      implements KVBlockingClient {
    private KVBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KVBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KVBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KVBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.RangeResponse range(com.xqbase.etcd4j.RangeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RANGE, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.PutResponse put(com.xqbase.etcd4j.PutRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PUT, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.DeleteRangeResponse deleteRange(com.xqbase.etcd4j.DeleteRangeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_RANGE, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.TxnResponse txn(com.xqbase.etcd4j.TxnRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TXN, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.CompactionResponse compact(com.xqbase.etcd4j.CompactionRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COMPACT, getCallOptions(), request);
    }
  }

  public static class KVFutureStub extends io.grpc.stub.AbstractStub<KVFutureStub>
      implements KVFutureClient {
    private KVFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KVFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KVFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KVFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.RangeResponse> range(
        com.xqbase.etcd4j.RangeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RANGE, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.PutResponse> put(
        com.xqbase.etcd4j.PutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PUT, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.DeleteRangeResponse> deleteRange(
        com.xqbase.etcd4j.DeleteRangeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_RANGE, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.TxnResponse> txn(
        com.xqbase.etcd4j.TxnRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TXN, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.CompactionResponse> compact(
        com.xqbase.etcd4j.CompactionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COMPACT, getCallOptions()), request);
    }
  }

  private static final int METHODID_RANGE = 0;
  private static final int METHODID_PUT = 1;
  private static final int METHODID_DELETE_RANGE = 2;
  private static final int METHODID_TXN = 3;
  private static final int METHODID_COMPACT = 4;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final KV serviceImpl;
    private final int methodId;

    public MethodHandlers(KV serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RANGE:
          serviceImpl.range((com.xqbase.etcd4j.RangeRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.RangeResponse>) responseObserver);
          break;
        case METHODID_PUT:
          serviceImpl.put((com.xqbase.etcd4j.PutRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.PutResponse>) responseObserver);
          break;
        case METHODID_DELETE_RANGE:
          serviceImpl.deleteRange((com.xqbase.etcd4j.DeleteRangeRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.DeleteRangeResponse>) responseObserver);
          break;
        case METHODID_TXN:
          serviceImpl.txn((com.xqbase.etcd4j.TxnRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.TxnResponse>) responseObserver);
          break;
        case METHODID_COMPACT:
          serviceImpl.compact((com.xqbase.etcd4j.CompactionRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.CompactionResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final KV serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_RANGE,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.RangeRequest,
              com.xqbase.etcd4j.RangeResponse>(
                serviceImpl, METHODID_RANGE)))
        .addMethod(
          METHOD_PUT,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.PutRequest,
              com.xqbase.etcd4j.PutResponse>(
                serviceImpl, METHODID_PUT)))
        .addMethod(
          METHOD_DELETE_RANGE,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.DeleteRangeRequest,
              com.xqbase.etcd4j.DeleteRangeResponse>(
                serviceImpl, METHODID_DELETE_RANGE)))
        .addMethod(
          METHOD_TXN,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.TxnRequest,
              com.xqbase.etcd4j.TxnResponse>(
                serviceImpl, METHODID_TXN)))
        .addMethod(
          METHOD_COMPACT,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.CompactionRequest,
              com.xqbase.etcd4j.CompactionResponse>(
                serviceImpl, METHODID_COMPACT)))
        .build();
  }
}
