package com.github.yoo20370.enrollment.enrollment.controller.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CourseStudentResponse {
    private final String courseId;
    private final String courseTitle;
    private final int totalConfirmedCount;
    private final List<StudentInfo> students;

    public static CourseStudentResponse of(String courseId, String courseTitle, int totalConfirmedCount, List<StudentInfo> students) {
        return CourseStudentResponse.builder()
            .courseId(courseId)
            .courseTitle(courseTitle)
            .totalConfirmedCount(totalConfirmedCount)
            .students(students)
            .build();
    }
}
