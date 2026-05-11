package com.github.yoo20370.enrollment.course.service.command;

import com.github.yoo20370.enrollment.course.controller.request.CreateCourseRequest;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateCourseCommand {

    private String title;
    private String description;
    private Long price;
    private Integer capacity;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static CreateCourseCommand of(CreateCourseRequest request) {
        return new CreateCourseCommand(
            request.getTitle(),
            request.getDescription(),
            request.getPrice(),
            request.getCapacity(),
            request.getStartAt(),
            request.getEndAt()
        );
    }
}
