package com.github.yoo20370.enrollment.enrollment.controller;

import com.github.yoo20370.enrollment.enrollment.controller.request.CancelEnrollmentRequest;
import com.github.yoo20370.enrollment.enrollment.controller.request.CreateEnrollmentRequest;
import com.github.yoo20370.enrollment.enrollment.controller.response.CancelEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.CreateEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.EnrollmentInfo;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface EnrollmentController {

    ResponseEntity<ApiResponse<CreateEnrollmentResponse>> enroll(
        @NotNull @RequestHeader("X-User-Id") String requesterId,
        @Valid @RequestBody CreateEnrollmentRequest request
    );

    ResponseEntity<ApiResponse<CancelEnrollmentResponse>> cancel(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @Valid @RequestBody CancelEnrollmentRequest request
    );

    ResponseEntity<ApiResponse<Page<EnrollmentInfo>>> findMyEnrollments(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    );

}
