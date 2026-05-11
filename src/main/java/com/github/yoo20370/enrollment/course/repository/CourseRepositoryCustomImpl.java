package com.github.yoo20370.enrollment.course.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class CourseRepositoryCustomImpl implements CourseRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CourseRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

}
