package com.github.yoo20370.enrollment.enrollment.repository;

import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import java.util.List;
import java.util.UUID;

public interface EnrollmentRepositoryCustom {

    List<Enrollment> findByCourseIdWithUser(UUID courseId);

}
