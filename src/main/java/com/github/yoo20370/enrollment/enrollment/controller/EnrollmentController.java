package com.github.yoo20370.enrollment.enrollment.controller;

import com.github.yoo20370.enrollment.enrollment.controller.request.CreateEnrollmentRequest;
import com.github.yoo20370.enrollment.enrollment.controller.response.CancelEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.CreateEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.EnrollmentInfo;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface EnrollmentController {

    ResponseEntity<ApiResponse<CreateEnrollmentResponse>> enroll(
        String requesterId,
        CreateEnrollmentRequest request
    );

    ResponseEntity<ApiResponse<CancelEnrollmentResponse>> cancel(
        String userId,
        String enrollmentId
    );

    ResponseEntity<ApiResponse<Page<EnrollmentInfo>>> findMyEnrollments(
        String userId,
        Pageable pageable
    );

}
