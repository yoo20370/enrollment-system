package com.github.yoo20370.enrollment.course.service;

import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.repository.CourseRepository;
import com.github.yoo20370.enrollment.course.service.command.CreateCourseCommand;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CreateCourseResponse create(CreateCourseCommand command, UUID userId) {

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        findUser.validateInstructor();

        Course newCourse = Course.create(
            userId,
            command.getTitle(),
            command.getDescription(),
            command.getPrice(),
            command.getCapacity(),
            command.getStartAt(),
            command.getEndAt()
        );

        Course savedCourse = courseRepository.save(newCourse);

        return CreateCourseResponse.of(
            savedCourse.getId(),
            savedCourse.getCreatedAt()
            );
    }

}
