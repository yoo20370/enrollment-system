package com.github.yoo20370.enrollment.enrollment.repository;

import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.enrollment.domain.EnrollmentStatus;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>, EnrollmentRepositoryCustom {

    Optional<Enrollment> findByUserIdAndCourseIdAndStatusNot(UUID userId, UUID courseId, EnrollmentStatus enrollmentStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Enrollment> findWithLockById(UUID enrollmentId);

    Page<Enrollment> findByUserId(UUID userId, Pageable pageable);
}
