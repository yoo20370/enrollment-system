package com.github.yoo20370.enrollment.course.controller.response;

import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UpdateCourseStatusResponse {

    private final String courseId;
    private final String previousStatus;
    private final String currentStatus;
    private final String modifiedAt;

    public static UpdateCourseStatusResponse of(UUID courseId, CourseStatus previousStatus,
        CourseStatus currentStatus, LocalDateTime modifiedAt) {
        return UpdateCourseStatusResponse.builder()
            .courseId(courseId.toString())
            .previousStatus(previousStatus.name())
            .currentStatus(currentStatus.name())
            .modifiedAt(modifiedAt.toString())
            .build();
    }
}
