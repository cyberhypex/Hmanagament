package com.billingService.billingService.gRPC;


import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillinggRPCService extends BillingServiceGrpc.BillingServiceImplBase
        {
                private static final Logger log = LoggerFactory.getLogger(BillinggRPCService.class);

                @Override
                public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver){

                        log.info("Billing Account creation request recieved {}",billingRequest.toString());

                        //-> Business logic - e.g. save to db, perform calculation etc

                        BillingResponse response= BillingResponse.newBuilder().setAccountId("12345").setStatus("ACTIVE").build();
                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                }
}
