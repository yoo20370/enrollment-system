package com.github.yoo20370.enrollment.enrollment.domain;



import static org.assertj.core.api.Assertions.*;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.enrollment.exception.EnrollmentException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.domain.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EnrollmentTest {

    @DisplayName("결제 확정 후 취소 가능 기간 이내라면 예외가 발생하지 않는다")
    @Test
    void validateCancellable_success() {

        User user = Mockito.mock(User.class);
        Course course = Mockito.mock(Course.class);

        // given
        Enrollment enrollment = Enrollment.builder()
            .user(user)
            .course(course)
            .status(EnrollmentStatus.CANCELED)
            .confirmedAt(LocalDateTime.of(2026, 5, 5, 0, 0, 0))
            .build();

        // when then
        enrollment.validateCancellable(
            LocalDateTime.of(2026, 5, 5, 0, 0, 0),
            7
        );

    }

    @DisplayName("결제 확정 후 취소 가능 기간이 지나면 ENROLLMENT_CANCELLATION_EXPIRED 예외가 발생한다")
    @Test
    void validateCancellable_fail() {

        User user = Mockito.mock(User.class);
        Course course = Mockito.mock(Course.class);

        // given
        Enrollment enrollment = Enrollment.builder()
            .user(user)
            .course(course)
            .status(EnrollmentStatus.CANCELED)
            .confirmedAt(LocalDateTime.of(2026, 5, 5, 0, 0, 0))
            .build();

        // when then
        assertThatThrownBy(
            () -> enrollment.validateCancellable(
                LocalDateTime.of(2026, 5, 13,0,0,0),
                7
            )
        )
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ENROLLMENT_CANCELLATION_EXPIRED);

    }
}