package com.github.yoo20370.enrollment.enrollment.service;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.exception.CourseException;
import com.github.yoo20370.enrollment.course.repository.CourseRepository;
import com.github.yoo20370.enrollment.enrollment.repository.EnrollmentRepository;
import com.github.yoo20370.enrollment.enrollment.controller.response.CreateEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.enrollment.exception.EnrollmentException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public CreateEnrollmentResponse enroll(UUID courseId, UUID requesterId) {

        User user = userRepository.findById(requesterId)
            .orElseThrow(
                () -> new UserException(ErrorCode.USER_NOT_FOUND)
            );
        user.validateStudent();

        Course course = courseRepository.findWithLockById(courseId)
            .orElseThrow(
                () -> new CourseException(ErrorCode.COURSE_NOT_FOUND)
            );
        course.validateCapacity();
        course.validateStatus();

        Optional<Enrollment> result = enrollmentRepository.findByUserIdAndCourseId(
            requesterId,
            courseId
        );

        if (result.isPresent()) {
            throw new EnrollmentException(ErrorCode.ENROLLMENT_ALREADY_ENROLLED);
        }

        course.increaseCount();
        Enrollment enrollment = Enrollment.create(user, course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return CreateEnrollmentResponse.of(savedEnrollment);
    }
}
