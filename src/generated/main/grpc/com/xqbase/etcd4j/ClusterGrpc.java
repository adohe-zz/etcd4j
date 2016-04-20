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
public class ClusterGrpc {

  private ClusterGrpc() {}

  public static final String SERVICE_NAME = "etcd4j.Cluster";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.MemberAddRequest,
      com.xqbase.etcd4j.MemberAddResponse> METHOD_MEMBER_ADD =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.Cluster", "MemberAdd"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberAddRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberAddResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.MemberRemoveRequest,
      com.xqbase.etcd4j.MemberRemoveResponse> METHOD_MEMBER_REMOVE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.Cluster", "MemberRemove"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberRemoveRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberRemoveResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.MemberUpdateRequest,
      com.xqbase.etcd4j.MemberUpdateResponse> METHOD_MEMBER_UPDATE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.Cluster", "MemberUpdate"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberUpdateRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberUpdateResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.xqbase.etcd4j.MemberListRequest,
      com.xqbase.etcd4j.MemberListResponse> METHOD_MEMBER_LIST =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "etcd4j.Cluster", "MemberList"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberListRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.xqbase.etcd4j.MemberListResponse.getDefaultInstance()));

  public static ClusterStub newStub(io.grpc.Channel channel) {
    return new ClusterStub(channel);
  }

  public static ClusterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ClusterBlockingStub(channel);
  }

  public static ClusterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ClusterFutureStub(channel);
  }

  public static interface Cluster {

    public void memberAdd(com.xqbase.etcd4j.MemberAddRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberAddResponse> responseObserver);

    public void memberRemove(com.xqbase.etcd4j.MemberRemoveRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberRemoveResponse> responseObserver);

    public void memberUpdate(com.xqbase.etcd4j.MemberUpdateRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberUpdateResponse> responseObserver);

    public void memberList(com.xqbase.etcd4j.MemberListRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberListResponse> responseObserver);
  }

  public static interface ClusterBlockingClient {

    public com.xqbase.etcd4j.MemberAddResponse memberAdd(com.xqbase.etcd4j.MemberAddRequest request);

    public com.xqbase.etcd4j.MemberRemoveResponse memberRemove(com.xqbase.etcd4j.MemberRemoveRequest request);

    public com.xqbase.etcd4j.MemberUpdateResponse memberUpdate(com.xqbase.etcd4j.MemberUpdateRequest request);

    public com.xqbase.etcd4j.MemberListResponse memberList(com.xqbase.etcd4j.MemberListRequest request);
  }

  public static interface ClusterFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberAddResponse> memberAdd(
        com.xqbase.etcd4j.MemberAddRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberRemoveResponse> memberRemove(
        com.xqbase.etcd4j.MemberRemoveRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberUpdateResponse> memberUpdate(
        com.xqbase.etcd4j.MemberUpdateRequest request);

    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberListResponse> memberList(
        com.xqbase.etcd4j.MemberListRequest request);
  }

  public static class ClusterStub extends io.grpc.stub.AbstractStub<ClusterStub>
      implements Cluster {
    private ClusterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ClusterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClusterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ClusterStub(channel, callOptions);
    }

    @java.lang.Override
    public void memberAdd(com.xqbase.etcd4j.MemberAddRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberAddResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MEMBER_ADD, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void memberRemove(com.xqbase.etcd4j.MemberRemoveRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberRemoveResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MEMBER_REMOVE, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void memberUpdate(com.xqbase.etcd4j.MemberUpdateRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberUpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MEMBER_UPDATE, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void memberList(com.xqbase.etcd4j.MemberListRequest request,
        io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MEMBER_LIST, getCallOptions()), request, responseObserver);
    }
  }

  public static class ClusterBlockingStub extends io.grpc.stub.AbstractStub<ClusterBlockingStub>
      implements ClusterBlockingClient {
    private ClusterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ClusterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClusterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ClusterBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.MemberAddResponse memberAdd(com.xqbase.etcd4j.MemberAddRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MEMBER_ADD, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.MemberRemoveResponse memberRemove(com.xqbase.etcd4j.MemberRemoveRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MEMBER_REMOVE, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.MemberUpdateResponse memberUpdate(com.xqbase.etcd4j.MemberUpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MEMBER_UPDATE, getCallOptions(), request);
    }

    @java.lang.Override
    public com.xqbase.etcd4j.MemberListResponse memberList(com.xqbase.etcd4j.MemberListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MEMBER_LIST, getCallOptions(), request);
    }
  }

  public static class ClusterFutureStub extends io.grpc.stub.AbstractStub<ClusterFutureStub>
      implements ClusterFutureClient {
    private ClusterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ClusterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClusterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ClusterFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberAddResponse> memberAdd(
        com.xqbase.etcd4j.MemberAddRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MEMBER_ADD, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberRemoveResponse> memberRemove(
        com.xqbase.etcd4j.MemberRemoveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MEMBER_REMOVE, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberUpdateResponse> memberUpdate(
        com.xqbase.etcd4j.MemberUpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MEMBER_UPDATE, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.xqbase.etcd4j.MemberListResponse> memberList(
        com.xqbase.etcd4j.MemberListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MEMBER_LIST, getCallOptions()), request);
    }
  }

  private static final int METHODID_MEMBER_ADD = 0;
  private static final int METHODID_MEMBER_REMOVE = 1;
  private static final int METHODID_MEMBER_UPDATE = 2;
  private static final int METHODID_MEMBER_LIST = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final Cluster serviceImpl;
    private final int methodId;

    public MethodHandlers(Cluster serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MEMBER_ADD:
          serviceImpl.memberAdd((com.xqbase.etcd4j.MemberAddRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberAddResponse>) responseObserver);
          break;
        case METHODID_MEMBER_REMOVE:
          serviceImpl.memberRemove((com.xqbase.etcd4j.MemberRemoveRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberRemoveResponse>) responseObserver);
          break;
        case METHODID_MEMBER_UPDATE:
          serviceImpl.memberUpdate((com.xqbase.etcd4j.MemberUpdateRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberUpdateResponse>) responseObserver);
          break;
        case METHODID_MEMBER_LIST:
          serviceImpl.memberList((com.xqbase.etcd4j.MemberListRequest) request,
              (io.grpc.stub.StreamObserver<com.xqbase.etcd4j.MemberListResponse>) responseObserver);
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
      final Cluster serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_MEMBER_ADD,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.MemberAddRequest,
              com.xqbase.etcd4j.MemberAddResponse>(
                serviceImpl, METHODID_MEMBER_ADD)))
        .addMethod(
          METHOD_MEMBER_REMOVE,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.MemberRemoveRequest,
              com.xqbase.etcd4j.MemberRemoveResponse>(
                serviceImpl, METHODID_MEMBER_REMOVE)))
        .addMethod(
          METHOD_MEMBER_UPDATE,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.MemberUpdateRequest,
              com.xqbase.etcd4j.MemberUpdateResponse>(
                serviceImpl, METHODID_MEMBER_UPDATE)))
        .addMethod(
          METHOD_MEMBER_LIST,
          asyncUnaryCall(
            new MethodHandlers<
              com.xqbase.etcd4j.MemberListRequest,
              com.xqbase.etcd4j.MemberListResponse>(
                serviceImpl, METHODID_MEMBER_LIST)))
        .build();
  }
}
