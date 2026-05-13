package com.github.yoo20370.enrollment.enrollment.repository;

import static com.github.yoo20370.enrollment.course.domain.QCourse.*;
import static com.github.yoo20370.enrollment.enrollment.domain.QEnrollment.*;
import static com.github.yoo20370.enrollment.user.domain.QUser.*;

import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.enrollment.domain.EnrollmentStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;

public class EnrollmentRepositoryCustomImpl implements EnrollmentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public EnrollmentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Enrollment> findByCourseIdWithUser(UUID courseId) {
        return queryFactory
            .selectFrom(enrollment)
            .join(enrollment.user, user).fetchJoin()
            .where(enrollment.course.id.eq(courseId)
                .and(enrollment.status.eq(EnrollmentStatus.CONFIRMED)))
            .fetch();
    }
}
