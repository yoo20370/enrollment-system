package com.github.yoo20370.enrollment.payment.controller.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "enrollmentId는 필수값입니다.")
    private String enrollmentId;

}
