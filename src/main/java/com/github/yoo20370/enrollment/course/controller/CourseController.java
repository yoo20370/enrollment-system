package com.github.yoo20370.enrollment.course.controller;

import com.github.yoo20370.enrollment.course.controller.request.CreateCourseRequest;
import com.github.yoo20370.enrollment.course.controller.response.CourseDetail;
import com.github.yoo20370.enrollment.course.controller.response.CourseInfo;
import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.course.controller.response.UpdateCourseStatusResponse;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import com.github.yoo20370.enrollment.enrollment.controller.response.CourseStudentResponse;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CourseController {

    ResponseEntity<ApiResponse<CreateCourseResponse>> create(
        String requesterId,
        CreateCourseRequest request
    );

    ResponseEntity<ApiResponse<Page<CourseInfo>>> findCourses(
        CourseStatus status,
        Pageable pageable
    );

    ResponseEntity<ApiResponse<CourseDetail>> findCourse(String courseId);

    ResponseEntity<ApiResponse<CourseStudentResponse>> findCourseStudents(
        String userId,
        String courseId
    );

    ResponseEntity<ApiResponse<UpdateCourseStatusResponse>> open(
        String userId,
        String courseId
    );

    ResponseEntity<ApiResponse<UpdateCourseStatusResponse>> close(
        String userId,
        String courseId
    );

}
