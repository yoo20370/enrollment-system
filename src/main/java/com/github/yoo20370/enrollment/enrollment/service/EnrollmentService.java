package com.github.yoo20370.enrollment.enrollment.service;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.exception.CourseException;
import com.github.yoo20370.enrollment.course.repository.CourseRepository;
import com.github.yoo20370.enrollment.enrollment.controller.response.CancelEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.EnrollmentInfo;
import com.github.yoo20370.enrollment.enrollment.domain.EnrollmentStatus;
import com.github.yoo20370.enrollment.enrollment.repository.EnrollmentRepository;
import com.github.yoo20370.enrollment.enrollment.controller.response.CreateEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.enrollment.exception.EnrollmentException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.payment.domain.Payment;
import com.github.yoo20370.enrollment.payment.exception.PaymentException;
import com.github.yoo20370.enrollment.payment.repository.PaymentRepository;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
public class EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;
    private final Clock clock;

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

        Optional<Enrollment> result = enrollmentRepository.findByUserIdAndCourseIdAndStatusNot(
            requesterId,
            courseId,
            EnrollmentStatus.CANCELED
        );

        if (result.isPresent()) {
            throw new EnrollmentException(ErrorCode.ENROLLMENT_ALREADY_ENROLLED);
        }

        course.increaseCount();
        Enrollment enrollment = Enrollment.create(user, course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return CreateEnrollmentResponse.of(savedEnrollment);
    }

    @Transactional
    public CancelEnrollmentResponse cancel(UUID enrollmentId, UUID userId) {

        LocalDateTime now = LocalDateTime.now(clock);

        User user = userRepository.findById(userId)
            .orElseThrow(
                () -> new UserException(ErrorCode.USER_NOT_FOUND)
            );
        user.validateActiveStatus();

        Enrollment enrollment = enrollmentRepository.findWithLockById(enrollmentId)
            .orElseThrow(
                () -> new EnrollmentException(ErrorCode.ENROLLMENT_NOT_FOUND)
            );
        enrollment.validateConfirmed();
        enrollment.validateCancellable(now, 7);
        enrollment.validateOwner(userId);

        Payment payment = paymentRepository.findByEnrollmentId(enrollmentId)
            .orElseThrow(
                () -> new PaymentException(ErrorCode.PAYMENT_NOT_FOUND)
            );
        payment.validatePaid();

        Course course = courseRepository.findWithLockById(enrollment.getCourse().getId())
                .orElseThrow(
                    () -> new CourseException(ErrorCode.COURSE_NOT_FOUND)
                );
        payment.cancel(now);
        course.decreaseCount();
        enrollment.cancel(now);

        return CancelEnrollmentResponse.of(enrollment, course, payment);
    }

    public Page<EnrollmentInfo> findMyEnrollments(UUID userId, Pageable pageable) {

        User user = userRepository.findById(userId)
            .orElseThrow(
                () -> new UserException(ErrorCode.USER_NOT_FOUND)
            );
        user.validateActiveStatus();

        Page<Enrollment> enrollmentPage = enrollmentRepository.findByUserId(userId, pageable);

        List<UUID> courseIds = enrollmentPage.getContent().stream()
            .map(e -> e.getCourse().getId())
            .distinct()
            .toList();

        Map<UUID, Course> courseMap = courseRepository.findAllById(courseIds)
            .stream()
            .collect(Collectors.toMap(Course::getId, c -> c));

        return enrollmentPage.map(e -> {
            Course course = courseMap.get(e.getCourse().getId());
            return EnrollmentInfo.of(e, course);
        });
    }
}
