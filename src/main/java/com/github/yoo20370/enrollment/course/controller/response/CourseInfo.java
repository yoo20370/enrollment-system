package com.github.yoo20370.enrollment.course.controller.response;

import com.github.yoo20370.enrollment.course.domain.Course;
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
public class CourseInfo {
    private String courseId;
    private String title;
    private Long price;
    private Integer capacity;
    private Integer currentCount;
    private String status;
    private String instructor;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static CourseInfo of(Course course, String instructorName) {
        return CourseInfo.builder()
            .courseId(course.getId().toString())
            .title(course.getTitle())
            .price(course.getPrice())
            .capacity(course.getCapacity())
            .currentCount(course.getCurrentCount())
            .status(course.getStatus().getDescription())
            .instructor(instructorName)
            .startAt(course.getStartAt())
            .endAt(course.getEndAt())
            .build();
    }
}
