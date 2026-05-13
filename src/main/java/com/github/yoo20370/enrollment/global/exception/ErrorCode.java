package com.github.yoo20370.enrollment.global.exception;

public enum ErrorCode {
    // Common
    COMMON_INVALID_INPUT(400, "잘못된 입력값입니다."),
    COMMON_UNAUTHORIZED(401, "유효하지 않은 사용자입니다."),
    COMMON_FORBIDDEN(403, "권한이 없습니다."),

    // User
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    USER_INVALID_ROLE(400, "존재하지 않는 역할입니다."),
    USER_DUPLICATE_EMAIL(409, "이미 사용 중인 이메일입니다."),
    USER_INVALID_STATUS(400, "유효하지 않은 사용자 상태입니다."),
    USER_NOT_MATCH(409, "사용자가 일치하지 않습니다."),

    // Course
    COURSE_INVALID_PRICE(400, "강의 가격은 0 이상이어야 합니다."),
    COURSE_INVALID_CAPACITY(400, "강의 정원은 0 이상이어야 합니다."),
    COURSE_INVALID_START_DATE(400, "시작일은 오늘 이후여야 합니다."),
    COURSE_INVALID_END_DATE(400, "종료일은 시작일 이후여야 합니다."),
    COURSE_NOT_FOUND(404, "강의를 찾을 수 없습니다."),
    COURSE_NOT_OPEN(400, "신청 가능한 강의가 아닙니다."),
    COURSE_FULL(409, "정원이 초과되었습니다."),
    COURSE_INVALID_STATUS(409, "강의 상태가 유효하지 않습니다."),
    COURSE_STATUS_UPDATE_FAIL(409, "강의 상태 변경에 실패했습니다."),

    // Enrollment
    ENROLLMENT_NOT_FOUND(404, "신청 정보를 찾을 수 없습니다."),
    ENROLLMENT_ALREADY_ENROLLED(409, "이미 신청한 강의입니다."),
    ENROLLMENT_ALREADY_CANCELLED(409, "이미 취소된 수강 신청입니다."),
    ENROLLMENT_CANCELLATION_EXPIRED(409, "취소 가능 기간이 지났습니다."),
    ENROLLMENT_INVALID_STATUS(409, "유효한 신청 상태가 아닙니다."),

    // Payment
    PAYMENT_NOT_FOUND(404, "결제 정보를 찾을 수 없습니다."),
    PAYMENT_ALREADY_PAID(409, "이미 결제된 수강 신청입니다."),
    PAYMENT_FAILED(500, "결제에 실패했습니다."),
    PAYMENT_INVALID_STATUS(409, "유효하지 않은 결제 상태입니다."),
    PAYMENT_ALREADY_CANCELLED(409, "이미 취소된 결제입니다."),

    INTERNAL_SERVER_ERROR(500,"서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
