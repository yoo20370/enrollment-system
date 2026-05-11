package com.github.yoo20370.enrollment.payment.domain;

public enum Provider {

    TOSS("토스");

    private final String description;

    Provider(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
