package com.github.yoo20370.enrollment.enrollment.controller.response;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CancelEnrollmentResponse {

    private final String enrollmentId;
    private final String courseId;
    private final String enrollmentStatus;
    private final String paymentId;
    private final Long amount;
    private final String cancelledAt;
    private final String paymentStatus;

    public static CancelEnrollmentResponse of(Enrollment enrollment, Course course, Payment payment) {
        return CancelEnrollmentResponse.builder()
            .enrollmentId(enrollment.getId().toString())
            .courseId(course.getId().toString())
            .enrollmentStatus(enrollment.getStatus().getDescription())
            .paymentId(payment.getId().toString())
            .amount(payment.getAmount())
            .cancelledAt(payment.getCancelledAt().toString())
            .paymentStatus(payment.getStatus().getDescription())
            .build();
    }

}
