package com.github.yoo20370.enrollment.course.exception;

import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;

public class CourseException extends CustomException {

    public CourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
