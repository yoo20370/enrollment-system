package com.github.yoo20370.enrollment.course.repository;

import static com.github.yoo20370.enrollment.course.domain.QCourse.course;

import com.github.yoo20370.enrollment.course.domain.Course;
import com.github.yoo20370.enrollment.course.domain.CourseStatus;
import com.github.yoo20370.enrollment.course.domain.QCourse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class CourseRepositoryCustomImpl implements CourseRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CourseRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
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

    private BooleanExpression statusEq(CourseStatus status) {
        return status != null ? course.status.eq(status) : null;
    }
}
