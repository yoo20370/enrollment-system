package com.github.yoo20370.enrollment.course.controller;

import com.github.yoo20370.enrollment.course.controller.request.CreateCourseRequest;
import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CourseController {

    ResponseEntity<ApiResponse<CreateCourseResponse>> create(
        String requesterId,
        CreateCourseRequest request
    );

}
