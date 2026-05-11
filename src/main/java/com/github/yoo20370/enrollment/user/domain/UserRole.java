package com.github.yoo20370.enrollment.user.domain;

import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.exception.UserException;


public enum UserRole {

    ADMIN("관리자"),
    INSTRUCTOR("강사"),
    STUDENT("수강생");


    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public static UserRole from(String role) {
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UserException(ErrorCode.USER_INVALID_ROLE);
        }
    }

    public String getDescription() {
        return description;
    }
}
