package com.brunodunbar;

import com.brunodunbar.grpc.sandbox.ExtracaoGrpc;
import com.brunodunbar.grpc.sandbox.ExtracaoRequest;
import com.brunodunbar.grpc.sandbox.GRpcWrapper;
import com.brunodunbar.grpc.sandbox.Services;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@GRpcService
public class ExtracaoGRpcService extends ExtracaoGrpc.ExtracaoImplBase {

    public static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public StreamObserver<Services.ExtracaoRequest> extrair(StreamObserver<Services.ExtracaoResponse> responseObserver) {
        return new StreamObserver<Services.ExtracaoRequest>() {
            @Override
            public void onNext(Services.ExtracaoRequest request) {
                ExtracaoRequest req = GRpcWrapper.unwrap(request);

                LOGGER.info("msg received: " + req);

                responseObserver.onNext(Services.ExtracaoResponse.newBuilder()
                        .setId(req.getId()).setSuccess(true).build());
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("Falha na comunicação", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
