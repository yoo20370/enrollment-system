package com.github.yoo20370.enrollment.course.domain;

import com.github.yoo20370.enrollment.course.exception.CourseException;
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
@Getter
@Table(name = "courses")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @Column(name="title")
    private String title;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="price", nullable = false)
    private Long price;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "current_count", nullable = false)
    private Integer currentCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    public static Course create(User instructor, String title, String description,
        Long price, Integer capacity,
        LocalDateTime startAt, LocalDateTime endAt
        ) {

        if (price < 0) throw new CourseException(ErrorCode.COURSE_INVALID_PRICE);
        if (capacity <= 0) throw new CourseException(ErrorCode.COURSE_INVALID_CAPACITY);
        if (startAt.isBefore(LocalDateTime.now())) throw new CourseException(ErrorCode.COURSE_INVALID_START_DATE);
        if (endAt.isBefore(startAt)) throw new CourseException(ErrorCode.COURSE_INVALID_END_DATE);

        return Course.builder()
            .instructor(instructor)
            .title(title)
            .description(description)
            .price(price)
            .capacity(capacity)
            .currentCount(0)
            .status(CourseStatus.DRAFT)
            .startAt(startAt)
            .endAt(endAt)
            .build();
    }

}
