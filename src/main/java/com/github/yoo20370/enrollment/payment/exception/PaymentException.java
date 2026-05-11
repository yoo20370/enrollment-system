package com.github.yoo20370.enrollment.payment.exception;

import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;

public class PaymentException extends CustomException {

    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
