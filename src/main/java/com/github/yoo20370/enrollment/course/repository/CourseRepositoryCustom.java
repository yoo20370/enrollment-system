package com.github.yoo20370.enrollment.course.repository;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepositoryCustom {

    Page<Course> findAllByStatus(CourseStatus status, Pageable pageable);
}
