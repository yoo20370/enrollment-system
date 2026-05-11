package com.github.yoo20370.enrollment.payment.controller.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class PaymentResult {
    private final boolean success;
    private final String transactionId;
    private final LocalDateTime paidAt;
    private final String provider;


    public static PaymentResult success(String transactionId, LocalDateTime paidAt, String provider) {
        return PaymentResult.builder()
            .success(true)
            .transactionId(transactionId)
            .paidAt(paidAt)
            .provider(provider)
            .build();
    }

    public static PaymentResult failure() {
        return PaymentResult.builder()
            .success(false)
            .transactionId(null)
            .paidAt(null)
            .provider(null)
            .build();
    }

    public boolean isFailed() {
        return !success;
    }

}
