package com.github.yoo20370.enrollment.enrollment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.exception.CourseException;
import com.github.yoo20370.enrollment.course.repository.CourseRepository;
import com.github.yoo20370.enrollment.enrollment.controller.response.CancelEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.CreateEnrollmentResponse;
import com.github.yoo20370.enrollment.enrollment.controller.response.EnrollmentInfo;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;

import com.github.yoo20370.enrollment.enrollment.domain.EnrollmentStatus;
import com.github.yoo20370.enrollment.enrollment.exception.EnrollmentException;
import com.github.yoo20370.enrollment.enrollment.repository.EnrollmentRepository;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.payment.domain.Payment;
import com.github.yoo20370.enrollment.payment.domain.PaymentStatus;
import com.github.yoo20370.enrollment.payment.exception.PaymentException;
import com.github.yoo20370.enrollment.payment.repository.PaymentRepository;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EnrollmentServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    EnrollmentRepository enrollmentRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    Clock clock;

    @InjectMocks
    EnrollmentService enrollmentService;

    @DisplayName("유효한 수강생이 OPEN 상태의 강의를 신청하면 성공하고 PENDING 상태를 갖는 Enrollment가 생성된다.")
    @Test
    void enroll_success() {
        // given
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();
        User user = mock(User.class);
        Course course = mock(Course.class);

        UUID savedEnrollmentId = UUID.randomUUID();
        User savedUser = mock(User.class);
        Course savedCourse = mock(Course.class);
        Enrollment savedEnrollment = mock(Enrollment.class);

        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));
        when(courseRepository.findWithLockById(courseId))
            .thenReturn(Optional.of(course));
        when(enrollmentRepository.findByUserIdAndCourseIdAndStatusNot(userId, courseId, EnrollmentStatus.CANCELED))
            .thenReturn(Optional.empty());
        when(enrollmentRepository.save(any(Enrollment.class)))
            .thenReturn(savedEnrollment);

        when(savedEnrollment.getId()).thenReturn(savedEnrollmentId);
        when(savedEnrollment.getCourse()).thenReturn(savedCourse);
        when(savedCourse.getId()).thenReturn(courseId);
        when(savedEnrollment.getUser()).thenReturn(savedUser);
        when(savedUser.getId()).thenReturn(userId);
        when(savedEnrollment.getStatus()).thenReturn(EnrollmentStatus.PENDING);
        when(savedEnrollment.getCreatedAt()).thenReturn(LocalDateTime.now());

        // when
        CreateEnrollmentResponse response = enrollmentService.enroll(courseId, userId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getEnrollmentId()).isEqualTo(savedEnrollmentId.toString());
        assertThat(response.getStatus()).isEqualTo(EnrollmentStatus.PENDING.name());
        Mockito.verify(course).increaseCount();
        Mockito.verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @DisplayName("수강 신청 요청의 userId에 해당하는 User가 존재하지 않으면 ErrorCode.USER_NOT_FOUND를 던진다.")
    @Test
    void enroll_fail_with_USER_NOT_FOUND() {
        // given
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId))
            .thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(UserException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("수강생이 아닌 사용자가 강의를 신청하면 ErrorCode.COMMON_FORBIDDEN을 던진다.")
    @Test
    void enroll_fail_with_COMMON_FORBIDDEN() {
        // given
        User user = mock(User.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));
        doThrow(new UserException(ErrorCode.COMMON_FORBIDDEN))
            .when(user).validateStudent();

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(UserException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.COMMON_FORBIDDEN);
    }

    @DisplayName("비활성 유저가 강의를 신청하면 ErrorCode.USER_INVALID_STATUS를 던진다.")
    @Test
    void enroll_fail_with_USER_INVALID_STATUS() {
        // given
        User user = mock(User.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doThrow(new UserException(ErrorCode.USER_INVALID_STATUS)).when(user).validateStudent();

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(UserException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.USER_INVALID_STATUS);
    }

    @DisplayName("강의가 존재하지 않으면 ErrorCode.COURSE_NOT_FOUND를 던진다.")
    @Test
    void enroll_fail_with_COURSE_NOT_FOUND() {

        // given
        User user = mock(User.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(courseRepository.findWithLockById(courseId)).thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(CourseException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.COURSE_NOT_FOUND);
    }

    @DisplayName("수강 신청 시, 정원이 초과된 경우 ErrorCode.COURSE_FULL을 던진다.")
    @Test
    void enroll_fail_with_COURSE_FULL() {

        // given
        User user = mock(User.class);
        Course course = mock(Course.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(courseRepository.findWithLockById(courseId)).thenReturn(Optional.of(course));
        doThrow(new CourseException(ErrorCode.COURSE_FULL)).when(course).validateCapacity();

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(CourseException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.COURSE_FULL);
    }

    @DisplayName("수강 신청 시, 강의 상태가 OPEN이 아니라면 COURSE_NOT_OPEN을 던진다.")
    @Test
    void enroll_fail_with_COUSE_NOT_OPEN() {

        // given
        User user = mock(User.class);
        Course course = mock(Course.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(courseRepository.findWithLockById(courseId)).thenReturn(Optional.of(course));
        doThrow(new CourseException(ErrorCode.COURSE_NOT_OPEN)).when(course).validateStatus();

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(CourseException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.COURSE_NOT_OPEN);
    }

    @DisplayName("수강 신청 시, 이미 신청한 강의인 경우 ENROLLMENT_ALREADY_ENROLLED를 던진다.")
    @Test
    void enroll_fail_with_ENROLLMENT_ALREADY_ENROLLED() {

        // given
        User user = mock(User.class);
        Course course = mock(Course.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(courseRepository.findWithLockById(courseId)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByUserIdAndCourseIdAndStatusNot(userId, courseId, EnrollmentStatus.CANCELED))
            .thenReturn(Optional.of(enrollment));

        // when then
        assertThatThrownBy(() -> enrollmentService.enroll(courseId, userId))
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ENROLLMENT_ALREADY_ENROLLED);
    }

    @Test
    @DisplayName("정상 수강신청 취소 성공")
    void cancel_success() {
        // given
        User user = mock(User.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();

        Payment payment = mock(Payment.class);
        Course cancelCourse = mock(Course.class);

        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(paymentRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.of(payment));
        when(courseRepository.findWithLockById(any())).thenReturn(Optional.of(cancelCourse));
        when(enrollment.getCourse()).thenReturn(cancelCourse);
        when(cancelCourse.getId()).thenReturn(courseId);

        when(enrollment.getId()).thenReturn(enrollmentId);
        when(cancelCourse.getId()).thenReturn(courseId);
        when(enrollment.getStatus()).thenReturn(EnrollmentStatus.CANCELED);
        when(enrollment.getCancelledAt()).thenReturn(LocalDateTime.of(2026,5,6,0,0,0));
        when(payment.getId()).thenReturn(UUID.randomUUID());
        when(payment.getAmount()).thenReturn(10000L);
        when(payment.getCancelledAt()).thenReturn(LocalDateTime.of(2026,5,6,0,0,0));
        when(payment.getStatus()).thenReturn(PaymentStatus.CANCELLED);

        // when
        CancelEnrollmentResponse response = enrollmentService.cancel(enrollmentId, userId);

        // then
        assertThat(response).isNotNull();
        verify(payment).cancel(any(LocalDateTime.class));
        verify(cancelCourse).decreaseCount();
        verify(enrollment).cancel(any(LocalDateTime.class));
    }

    @DisplayName("수강 취소 요청의 userId에 해당하는 User가 존재하지 않으면 ErrorCode.USER_NOT_FOUND를 던진다.")
    @Test
    void cancel_fail_with_USER_NOT_FOUND() {
        // given
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();

        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId))
            .thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(UserException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("강의 취소시, 신청이 존재하지 않으면 ErrorCode.ENROLLMENT_NOT_FOUND를 던진다.")
    @Test
    void cancel_fail_with_ENROLLMENT_NOT_FOUND() {

        // given
        User user = mock(User.class);
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();

        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ENROLLMENT_NOT_FOUND);
    }

    @DisplayName("강의 취소시, 이미 취소된 신청인 경우 ErrorCode.ENROLLMENT_ALREADY_CANCELLED를 던진다.")
    @Test
    void cancel_fail_with_ENROLLMENT_ALREADY_CANCELLED() {

        // given
        User user = mock(User.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();


        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.of(enrollment));
        doThrow(new EnrollmentException(ErrorCode.ENROLLMENT_ALREADY_CANCELLED))
            .when(enrollment).validateConfirmed();

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ENROLLMENT_ALREADY_CANCELLED);
    }

    @DisplayName("강의 취소할 때, 신청 상태가 PENDING 상태라면 ENROLLMENT_INVALID_STATUS를 던진다.")
    @Test
    void cancel_fail_with_ENROLLMENT_INVALID_STATUS() {

        // given
        User user = mock(User.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();


        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.of(enrollment));
        doThrow(new EnrollmentException(ErrorCode.ENROLLMENT_INVALID_STATUS))
            .when(enrollment).validateConfirmed();

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ENROLLMENT_INVALID_STATUS);
    }

    @DisplayName("강의 취소할 때, 취소 기간을 지난 경우 ErrorCode.ENROLLMENT_CANCELLATION_EXPIRED를 던진다.")
    @Test
    void cancel_fail_with_ENROLLMENT_CANCELLATION_EXPIRED() {

        // given
        User user = mock(User.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();


        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.of(enrollment));
        doThrow(new EnrollmentException(ErrorCode.ENROLLMENT_CANCELLATION_EXPIRED))
            .when(enrollment).validateCancellable(any(LocalDateTime.class), any(Integer.class));

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ENROLLMENT_CANCELLATION_EXPIRED);
    }

    @DisplayName("강의 취소할 때, 수강신청 소유자가 아닌 경우 ErrorCode.COMMON_FORBIDDEN를 던진다.")
    @Test
    void cancel_fail_with_COMMON_FORBIDDEN() {

        // given
        User user = mock(User.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();


        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.of(enrollment));
        doThrow(new EnrollmentException(ErrorCode.COMMON_FORBIDDEN))
            .when(enrollment).validateOwner(userId);

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(EnrollmentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.COMMON_FORBIDDEN);
    }

    @DisplayName("강의 취소할 때, 결제 정보가 없다면 ErrorCode.PAYMENT_NOT_FOUND를 던진다.")
    @Test
    void cancel_fail_with_PAYMENT_NOT_FOUND() {

        // given
        User user = mock(User.class);
        Enrollment enrollment = mock(Enrollment.class);
        UUID userId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();


        Instant FIXED_TIME = Instant.parse("2026-05-05T00:00:00Z");

        when(clock.instant()).thenReturn(FIXED_TIME);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(enrollmentRepository.findWithLockById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(paymentRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> enrollmentService.cancel(enrollmentId, userId))
            .isInstanceOf(PaymentException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PAYMENT_NOT_FOUND);
    }

    @DisplayName("내 수강 신청 목록을 페이지네이션으로 조회하면 신청한 강의 목록이 반환된다")
    @Test
    void findMyEnrollments_success() {

        // given
        UUID userId = UUID.randomUUID();

        User user = mock(User.class);

        Course course1 = mock(Course.class);
        Course course2 = mock(Course.class);

        UUID courseId1 = UUID.randomUUID();
        UUID courseId2 = UUID.randomUUID();

        Enrollment enrollment1 = mock(Enrollment.class);
        Enrollment enrollment2 = mock(Enrollment.class);

        UUID enrollmentId1 = UUID.randomUUID();
        UUID enrollmentId2 = UUID.randomUUID();

        String title1 = "title1";
        String title2 = "title2";

        EnrollmentStatus status1 = EnrollmentStatus.CONFIRMED;
        EnrollmentStatus status2 = EnrollmentStatus.PENDING;

        LocalDateTime confirmedAt = LocalDateTime.of(2026, 5, 5, 0, 0, 0);

        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        when(enrollmentRepository.findByUserId(userId, pageable))
            .thenReturn(new PageImpl<>(List.of(enrollment1, enrollment2)));

        when(courseRepository.findAllById(anyList()))
            .thenReturn(List.of(course1, course2));

        when(enrollment1.getCourse())
            .thenReturn(course1);

        when(enrollment2.getCourse())
            .thenReturn(course2);

        when(course1.getId())
            .thenReturn(courseId1);

        when(course2.getId())
            .thenReturn(courseId2);

        when(enrollment1.getId())
            .thenReturn(enrollmentId1);

        when(enrollment2.getId())
            .thenReturn(enrollmentId2);

        when(course1.getTitle())
            .thenReturn(title1);

        when(course2.getTitle())
            .thenReturn(title2);

        when(enrollment1.getStatus())
            .thenReturn(status1);

        when(enrollment2.getStatus())
            .thenReturn(status2);

        when(enrollment1.getConfirmedAt())
            .thenReturn(confirmedAt);

        // when
        Page<EnrollmentInfo> result = enrollmentService.findMyEnrollments(userId, pageable);

        // /then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("두 번째 페이지 조회 시 신청 내역이 없으면 빈 목록이 반환된다.")
    @Test
    void findMyEnrollments_success2() {

        // given
        UUID userId = UUID.randomUUID();

        User user = mock(User.class);

        Pageable pageable = PageRequest.of(1, 10);

        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        when(enrollmentRepository.findByUserId(userId, pageable))
            .thenReturn(new PageImpl<>(List.of()));

        when(courseRepository.findAllById(anyList()))
            .thenReturn(List.of());

        // when
        Page<EnrollmentInfo> result = enrollmentService.findMyEnrollments(userId, pageable);

        // /then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(0);
    }
}