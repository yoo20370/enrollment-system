package com.github.yoo20370.enrollment.enrollment.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateEnrollmentRequest {

    @NotBlank(message = "courseId는 필수값입니다.")
    private String courseId;

}
