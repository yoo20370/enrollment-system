package com.github.yoo20370.enrollment.payment.controller.response;

import com.github.yoo20370.enrollment.payment.domain.Payment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class PaymentResponse {
    private final String paymentId;
    private final String enrollmentId;
    private final Long amount;
    private final LocalDateTime paidAt;
    private final String paymentStatus;

    public static PaymentResponse of(Payment payment) {
        return PaymentResponse.builder()
            .paymentId(payment.getId().toString())
            .enrollmentId(payment.getEnrollment().getId().toString())
            .amount(payment.getAmount())
            .paidAt(payment.getPaidAt())
            .paymentStatus(payment.getStatus().name())
            .build();
    }
}
