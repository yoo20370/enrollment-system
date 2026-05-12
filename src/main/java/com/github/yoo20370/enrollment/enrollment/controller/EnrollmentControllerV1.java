package com.github.yoo20370.enrollment.enrollment.controller;

import com.github.yoo20370.enrollment.enrollment.controller.request.CreateEnrollmentRequest;
import com.github.yoo20370.enrollment.enrollment.controller.response.CancelEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.CreateEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.EnrollmentInfo;
import com.github.yoo20370.enrollment.enrollment.service.EnrollmentService;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Validated
public class EnrollmentControllerV1 implements EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<CreateEnrollmentResponse>> enroll(
        @NotNull @RequestHeader("X-User-Id") String requesterId,
        @Valid @RequestBody CreateEnrollmentRequest request) {

        CreateEnrollmentResponse response = enrollmentService.enroll(
            convertUuidFrom(request.getCourseId()),
            convertUuidFrom(requesterId)
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<CancelEnrollmentResponse>> cancel(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @PathVariable("id") String enrollmentId) {

        CancelEnrollmentResponse response = enrollmentService.cancel(
            convertUuidFrom(enrollmentId),
            convertUuidFrom(userId)
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.of(response));
    }

    @GetMapping("/me")
    @Override
    public ResponseEntity<ApiResponse<Page<EnrollmentInfo>>> findMyEnrollments(
        @NotNull @RequestHeader("X-User-Id") String userId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<EnrollmentInfo> response = enrollmentService.findMyEnrollments(
            convertUuidFrom(userId),
            pageable
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.of(response));
    }

    private static UUID convertUuidFrom(String requesterId) {
        try {
            return UUID.fromString(requesterId);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.COMMON_INVALID_INPUT);
        }
    }
}
