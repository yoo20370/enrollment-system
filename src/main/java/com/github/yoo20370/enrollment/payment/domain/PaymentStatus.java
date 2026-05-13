package com.github.yoo20370.enrollment.payment.domain;

public enum PaymentStatus {

    PENDING("결제 대기"),
    PAID("결제 완료"),
    CANCELLED("결제 취소"),
    FAILED("결제 실패");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPaid() {
        return this == PAID;
    }

    public boolean isNotPaid() {
        return this != PAID;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }

}
