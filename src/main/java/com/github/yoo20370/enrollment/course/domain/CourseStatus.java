package com.github.yoo20370.enrollment.course.domain;

public enum CourseStatus {
    DRAFT("초안"),
    OPEN("모집 중"),
    CLOSED("모집 마감");

    private final String description;

    CourseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }



}
