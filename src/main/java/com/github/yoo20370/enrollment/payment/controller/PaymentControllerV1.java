package com.github.yoo20370.enrollment.payment.controller;

import com.github.yoo20370.enrollment.global.common.ApiResponse;
import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.payment.controller.request.PaymentRequest;
import com.github.yoo20370.enrollment.payment.controller.response.PaymentResponse;
import com.github.yoo20370.enrollment.payment.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Validated
public class PaymentControllerV1 implements PaymentController{

    private final PaymentService paymentService;

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<PaymentResponse>> pay(
        @NotNull @RequestHeader("X-User-Id") String requesterId,
        @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.pay(
            convertUuidFrom(request.getEnrollmentId()),
            convertUuidFrom(requesterId)
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    private static UUID convertUuidFrom(String requesterId) {
        try {
            return UUID.fromString(requesterId);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.COMMON_INVALID_INPUT);
        }
    }
}
