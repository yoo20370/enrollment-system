package com.github.yoo20370.enrollment.course.service;

import com.github.yoo20370.enrollment.course.controller.response.CourseInfo;
import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import com.github.yoo20370.enrollment.course.repository.CourseRepository;
import com.github.yoo20370.enrollment.course.service.command.CreateCourseCommand;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
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

    public Page<CourseInfo> findCourses(CourseStatus status, Pageable pageable) {

        Page<Course> coursePage = courseRepository.findAllByStatus(status, pageable);

        List<UUID> instructorIds = coursePage.getContent().stream()
            .map(Course::getInstructorId)
            .distinct()
            .toList();

        Map<UUID, User> userMap = userRepository.findAllById(instructorIds)
            .stream()
            .collect(Collectors.toMap(User::getId, u -> u));

        return coursePage.map(
            course -> CourseInfo.of(
                course,
                userMap.get(course.getInstructorId()).getName()
            )
        );
    }
}
