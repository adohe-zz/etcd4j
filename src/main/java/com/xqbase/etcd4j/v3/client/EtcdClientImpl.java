package com.xqbase.etcd4j.v3.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import com.xqbase.etcd4j.KVGrpc;
import com.xqbase.etcd4j.KVGrpc.KVFutureStub;
import com.xqbase.etcd4j.RangeRequest;
import com.xqbase.etcd4j.v3.requests.EtcdKeyRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Internal etcd client.
 *
 * @author Tony He
 */
public class EtcdClientImpl {

    private final ManagedChannel channel;
    private final KVFutureStub stub;

    /**
     * Construct internal client through host:port
     *
     * @param host
     * @param port
     */
    public EtcdClientImpl(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }

    /**
     * Construct internal client using the existing channel.
     *
     * @param channelBuilder
     */
    public EtcdClientImpl(ManagedChannelBuilder<?> channelBuilder) {
        this.channel = channelBuilder.build();
        this.stub = KVGrpc.newFutureStub(channel);
    }

    public ListenableFuture<?> key(EtcdKeyRequest request) {
        if (null == request) {

        }

        switch (request.getMethod()) {
            case HttpGet:
                RangeRequest req = RangeRequest.newBuilder().setKey(ByteString.copyFromUtf8(request.getKey())).build();
                return this.stub.range(req);
            case HttpPut:
        }
        return null;
    }
}
