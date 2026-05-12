package com.github.yoo20370.enrollment.course.service;

import com.github.yoo20370.enrollment.course.controller.response.CourseDetail;
import com.github.yoo20370.enrollment.course.controller.response.CourseInfo;
import com.github.yoo20370.enrollment.course.controller.response.CreateCourseResponse;
import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import com.github.yoo20370.enrollment.course.exception.CourseException;
import com.github.yoo20370.enrollment.course.repository.CourseRepository;
import com.github.yoo20370.enrollment.course.service.command.CreateCourseCommand;
import com.github.yoo20370.enrollment.enrollment.controller.response.CourseStudentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.StudentInfo;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.enrollment.repository.EnrollmentRepository;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.time.Clock;
import java.time.LocalDateTime;
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
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    @Transactional
    public CreateCourseResponse create(CreateCourseCommand command, UUID userId) {

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        findUser.validateInstructor();

        Course newCourse = Course.create(
            findUser,
            command.getTitle(),
            command.getDescription(),
            command.getPrice(),
            command.getCapacity(),
            command.getStartAt(),
            command.getEndAt(),
            LocalDateTime.now(clock)
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
            .map(course -> course.getInstructor().getId())
            .distinct()
            .toList();

        Map<UUID, User> userMap = userRepository.findAllById(instructorIds)
            .stream()
            .collect(Collectors.toMap(User::getId, u -> u));

        return coursePage.map(
            course -> {
                User instructor = userMap.get(course.getInstructor().getId());
                return CourseInfo.of(
                    course,
                    instructor != null ? instructor.getName() : null
                );
            }
        );
    }

    public CourseDetail findCourse(UUID courseId) {

        Course findCourse = courseRepository.findById(courseId)
            .orElseThrow(
                () -> new CourseException(ErrorCode.COURSE_NOT_FOUND)
            );

        return CourseDetail.of(findCourse);
    }

    public CourseStudentResponse findCourseStudents(UUID courseId, UUID userId) {

        User instructor = userRepository.findById(userId)
            .orElseThrow(
                () -> new UserException(ErrorCode.USER_NOT_FOUND)
            );
        instructor.validateInstructor();

        Course course = courseRepository.findById(courseId)
            .orElseThrow(
                () -> new CourseException(ErrorCode.COURSE_NOT_FOUND)
            );
        course.validateInstructor(userId);

        List<Enrollment> enrollments = enrollmentRepository.findByCourseIdWithUser(courseId);

        List<StudentInfo> students = enrollments.stream()
            .map(e -> {
                User student = e.getUser();
                return StudentInfo.of(
                    student.getId().toString(),
                    student.getName(),
                    student.getEmail()
                );
            })
            .toList();
        int confirmedCount = students.size();

        return CourseStudentResponse.of(
            courseId.toString(),
            course.getTitle(),
            confirmedCount,
            students
        );
    }
}
