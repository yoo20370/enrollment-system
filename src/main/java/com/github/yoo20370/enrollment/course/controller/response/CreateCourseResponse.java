package com.github.yoo20370.enrollment.course.controller.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateCourseResponse {

    private final UUID courseId;
    private final LocalDateTime createdAt;

    public static CreateCourseResponse of(UUID courseId, LocalDateTime createdAt) {
        return CreateCourseResponse.builder()
            .courseId(courseId)
            .createdAt(createdAt)
            .build();
    }
}
