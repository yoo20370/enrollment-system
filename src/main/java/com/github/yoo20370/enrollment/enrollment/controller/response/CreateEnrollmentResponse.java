package com.github.yoo20370.enrollment.enrollment.controller.response;

import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateEnrollmentResponse {

    private final String enrollmentId;
    private final String courseId;
    private final String userId;
    private final String status;
    private final String createdAt;

    public static CreateEnrollmentResponse of(Enrollment enrollment) {
        return CreateEnrollmentResponse.builder()
            .enrollmentId(enrollment.getId().toString())
            .courseId(enrollment.getCourse().getId().toString())
            .userId(enrollment.getUser().getId().toString())
            .status(enrollment.getStatus().name())
            .createdAt(enrollment.getCreatedAt().toString())
            .build();
    }
}
