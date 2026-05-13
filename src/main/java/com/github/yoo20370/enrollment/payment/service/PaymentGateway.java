package com.github.yoo20370.enrollment.payment.service;

import com.github.yoo20370.enrollment.payment.controller.response.PaymentResult;

public interface PaymentGateway {

    PaymentResult pay(Long amount);
}
