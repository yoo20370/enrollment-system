package com.github.yoo20370.enrollment.course.repository;

import static com.github.yoo20370.enrollment.course.domain.QCourse.course;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class CourseRepositoryCustomImpl implements CourseRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CourseRepositoryCustomImpl(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }

    @Override
    public Page<Course> findAllByStatus(CourseStatus status, Pageable pageable) {

        List<Course> content = queryFactory
            .selectFrom(course)
            .where(statusEq(status))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(course.count())
            .from(course)
            .where(statusEq(status));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public int open(UUID courseId, UUID userId) {

        long result = queryFactory
            .update(course)
            .set(course.status, CourseStatus.OPEN)
            .where(course.status.eq(CourseStatus.DRAFT)
                .and(course.instructor.id.eq(userId)
                    .and(course.id.eq(courseId))
                )
            )
            .execute();

        em.flush();
        em.clear();

        return (int) result;
    }

    @Override
    public int close(UUID courseId, UUID userId) {
        long result = queryFactory
            .update(course)
            .set(course.status, CourseStatus.CLOSED)
            .where(course.status.eq(CourseStatus.OPEN)
                .and(course.instructor.id.eq(userId)
                    .and(course.id.eq(courseId))
                )
            )
            .execute();

        em.flush();
        em.clear();

        return (int) result;
    }

    private BooleanExpression statusEq(CourseStatus status) {
        return status != null ? course.status.eq(status) : null;
    }
}
