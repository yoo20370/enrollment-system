package com.github.yoo20370.enrollment.course.controller.response;

import com.github.yoo20370.enrollment.course.domain.Course;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CourseDetail {

    private String courseId;
    private UUID instructorId;
    private String title;
    private String description;
    private Long price;
    private Integer capacity;
    private Integer currentCount;
    private String status;
    private String instructor;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static CourseDetail of(Course course
        ) {

        return CourseDetail.builder()
            .courseId(course.getId().toString())
            .instructorId(course.getInstructor().getId())
            .title(course.getTitle())
            .description(course.getDescription())
            .price(course.getPrice())
            .capacity(course.getCapacity())
            .currentCount(course.getCurrentCount())
            .status(course.getStatus().getDescription())
            .instructor(course.getInstructor().getName())
            .startAt(course.getStartAt())
            .endAt(course.getEndAt())
            .build();
    }
}
