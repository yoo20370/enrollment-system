package com.github.yoo20370.enrollment.enrollment.exception;

import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;

public class EnrollmentException extends CustomException {

    public EnrollmentException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
