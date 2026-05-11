package com.github.yoo20370.enrollment.course.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCourseRequest {

    @NotBlank(message = "강의 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "강의 설명은 필수입니다.")
    private String description;

    @NotNull(message = "강의 가격은 필수입니다.")
    @PositiveOrZero(message = "강의 가격은 0 이상이어야 합니다.")
    private Long price;

    @NotNull(message = "강의 정원은 필수입니다.")
    @Positive(message = "강의 정원은 0 이상이어야 합니다.")
    private Integer capacity;

    @NotNull(message = "시작일은 필수입니다.")
    private LocalDateTime startAt;

    @NotNull(message = "종료일은 필수입니다.")
    private LocalDateTime endAt;

    public static CreateCourseRequest of(String title, String description,
        Long price, Integer capacity,
        LocalDateTime startAt, LocalDateTime endAt
        ) {
        return CreateCourseRequest.builder()
            .title(title)
            .description(description)
            .price(price)
            .capacity(capacity)
            .startAt(startAt)
            .endAt(endAt)
            .build();
    }
}
