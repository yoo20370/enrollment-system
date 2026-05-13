package com.github.yoo20370.enrollment.course.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.github.yoo20370.enrollment.course.exception.CourseException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseTest {

    @DisplayName("수강 신청 시, 정원을 넘지 않았다면 예외가 발생하지 않는다.")
    @Test
    void validateCapacity_success() {
        // given
        Course course = Course.builder()
            .capacity(10)
            .currentCount(5)
            .build();

        // when then
        course.validateCapacity();

    }

    @DisplayName("수강 신청 시, 정원을 넘는다면 COURSE_FULL 예외가 발생한다")
    @Test
    void validateCapacity_fail() {
        // given
        Course course = Course.builder()
            .capacity(10)
            .currentCount(10)
            .build();

        // when then
        Assertions.assertThatThrownBy(
            () -> course.validateCapacity()
        ).isInstanceOf(CourseException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.COURSE_FULL);
    }
}