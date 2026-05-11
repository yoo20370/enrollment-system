package com.github.yoo20370.enrollment.course.controller;

import com.github.yoo20370.enrollment.course.controller.request.CreateCourseRequest;
import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.course.service.CourseService;
import com.github.yoo20370.enrollment.course.service.command.CreateCourseCommand;
import com.github.yoo20370.enrollment.global.common.ApiResponse;
import com.github.yoo20370.enrollment.global.exception.CustomException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseControllerV1 implements CourseController{

    private final CourseService courseService;

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<CreateCourseResponse>> create(
        @NotNull @RequestHeader("X-User-Id") String requesterId,
        @Valid @RequestBody CreateCourseRequest request) {

        UUID userId = getUuid(requesterId);

        CreateCourseResponse response = courseService.create(
            CreateCourseCommand.of(request),
            userId
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.of(response));
    }

    private static UUID getUuid(String requesterId) {
        try {
            return UUID.fromString(requesterId);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.COMMON_INVALID_INPUT);
        }
    }
}
