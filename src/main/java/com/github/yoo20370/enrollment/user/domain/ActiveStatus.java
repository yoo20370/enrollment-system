package com.github.yoo20370.enrollment.user.domain;

import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.exception.UserException;

public enum ActiveStatus {
    ACTIVE("활성 상태"),
    INACTIVE("비활성 상태"),
    WITHDRAWN("탈퇴 상태"),
    BANNED("정지 상태");

    private final String description;

    ActiveStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void validateActive() {
        if (this != ACTIVE) {
            throw new UserException(ErrorCode.USER_INVALID_STATUS);
        }
    }
}
