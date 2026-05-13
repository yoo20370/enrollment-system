package com.github.yoo20370.enrollment.course.controller;

import com.github.yoo20370.enrollment.course.controller.request.CreateCourseRequest;
import com.github.yoo20370.enrollment.course.controller.response.CourseDetail;
import com.github.yoo20370.enrollment.course.controller.response.CourseInfo;
import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.course.controller.response.UpdateCourseStatusResponse;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import com.github.yoo20370.enrollment.enrollment.controller.response.CourseStudentResponse;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface CourseController {

    ResponseEntity<ApiResponse<CreateCourseResponse>> create(
        @NotNull @RequestHeader("X-User-Id") String requesterId,
        @Valid @RequestBody CreateCourseRequest request
    );

    ResponseEntity<ApiResponse<Page<CourseInfo>>> findCourses(
        @RequestParam(required = false) CourseStatus status,
        Pageable pageable
    );

    ResponseEntity<ApiResponse<CourseDetail>> findCourse(
        @PathVariable("id")String courseId
    );

    ResponseEntity<ApiResponse<CourseStudentResponse>> findCourseStudents(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @PathVariable("id") String courseId
    );

    ResponseEntity<ApiResponse<UpdateCourseStatusResponse>> open(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @PathVariable("id") String courseId
    );

    ResponseEntity<ApiResponse<UpdateCourseStatusResponse>> close(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @PathVariable("id")String courseId
    );

}
