package com.github.yoo20370.enrollment.enrollment.controller.response;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class EnrollmentInfo {

    private final String enrollmentId;
    private final String courseId;
    private final String title;
    private final String status;
    private final String confirmedAt;
    private final String cancelledAt;

    public static EnrollmentInfo of(Enrollment enrollment, Course course) {
        return EnrollmentInfo.builder()
            .enrollmentId(enrollment.getId().toString())
            .courseId(course.getId().toString())
            .title(course.getTitle())
            .status(enrollment.getStatus().name())
            .confirmedAt(enrollment.getConfirmedAt() != null ? enrollment.getConfirmedAt().toString() : null )
            .cancelledAt(enrollment.getCancelledAt() != null ? enrollment.getCancelledAt().toString() : null)
            .build();
    }
}
