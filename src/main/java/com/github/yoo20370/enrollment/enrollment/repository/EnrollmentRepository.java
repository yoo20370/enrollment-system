package com.github.yoo20370.enrollment.enrollment.repository;

import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);
}
