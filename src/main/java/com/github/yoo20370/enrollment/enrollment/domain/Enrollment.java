package com.github.yoo20370.enrollment.enrollment.domain;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.enrollment.exception.EnrollmentException;
import com.github.yoo20370.enrollment.global.domain.BaseEntity;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="enrollment")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Enrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private EnrollmentStatus status;

    @Column(name="confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name="cancelled_at")
    private LocalDateTime cancelledAt;

    public static Enrollment create(User user, Course course) {
        return Enrollment.builder()
            .user(user)
            .course(course)
            .status(EnrollmentStatus.PENDING)
            .build();
    }

    public void validatePending() {
        if (status.isNotPending()) {
            throw new EnrollmentException(ErrorCode.ENROLLMENT_INVALID_STATUS);
        }
    }

    public void validateOwner(UUID userId) {
        if (!user.getId().equals(userId)) {
            throw new EnrollmentException(ErrorCode.COMMON_FORBIDDEN);
        }
    }

    public void confirm() {
        this.status = EnrollmentStatus.CONFIRMED;
    }
}
