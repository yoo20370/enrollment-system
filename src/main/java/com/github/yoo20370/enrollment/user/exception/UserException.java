package com.github.yoo20370.enrollment.user.exception;

import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;

public class UserException extends CustomException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
