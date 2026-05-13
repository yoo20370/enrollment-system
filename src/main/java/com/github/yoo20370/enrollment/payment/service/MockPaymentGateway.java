package com.github.yoo20370.enrollment.payment.service;

import com.github.yoo20370.enrollment.payment.controller.response.PaymentResult;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockPaymentGateway implements PaymentGateway {

    private final Clock clock;

    @Override
    public PaymentResult pay(Long amount) {
        return PaymentResult.success(
            UUID.randomUUID().toString(),
            LocalDateTime.now(clock),
            "TOSS"
        );
    }
}
