package com.github.yoo20370.enrollment.course.repository;

import com.github.yoo20370.enrollment.course.domain.Course;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, UUID>, CourseRepositoryCustom {

}
