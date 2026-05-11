package com.github.yoo20370.enrollment.payment.controller;


import com.github.yoo20370.enrollment.global.common.ApiResponse;
import com.github.yoo20370.enrollment.payment.controller.request.PaymentRequest;
import com.github.yoo20370.enrollment.payment.controller.response.PaymentResponse;
import org.springframework.http.ResponseEntity;

public interface PaymentController {

    ResponseEntity<ApiResponse<PaymentResponse>> pay(
        String requesterId,
        PaymentRequest request
    );
}
