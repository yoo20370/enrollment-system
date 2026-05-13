package com.github.yoo20370.enrollment.payment.controller;


import com.github.yoo20370.enrollment.global.common.ApiResponse;
import com.github.yoo20370.enrollment.payment.controller.request.PaymentRequest;
import com.github.yoo20370.enrollment.payment.controller.response.PaymentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface PaymentController {

    ResponseEntity<ApiResponse<PaymentResponse>> pay(
        @NotNull @RequestHeader("X-User-Id") String requesterId,
        @Valid @RequestBody PaymentRequest request
    );
}
