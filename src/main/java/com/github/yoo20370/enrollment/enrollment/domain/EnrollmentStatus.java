package com.github.yoo20370.enrollment.enrollment.domain;

public enum EnrollmentStatus {

    PENDING("대기"),
    CONFIRMED("확정"),
    CANCELED("취소");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isNotPending() {
        return !isPending();
    }
}
