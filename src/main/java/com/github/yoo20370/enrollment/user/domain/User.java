package com.github.yoo20370.enrollment.user.domain;

import com.github.yoo20370.enrollment.global.domain.BaseEntity;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private UserRole role;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name="active_status", nullable = false)
    private ActiveStatus activeStatus;

    public static User create(UserRole role, String email, String password, String name,
        String nickname) {
        return User.builder()
            .role(role)
            .email(email)
            .password(password)
            .name(name)
            .nickname(nickname)
            .activeStatus(ActiveStatus.ACTIVE)
            .build();
    }

    public void validateInstructor() {
        this.activeStatus.validateActive();
        if (this.role != UserRole.INSTRUCTOR) {
            throw new UserException(ErrorCode.COMMON_FORBIDDEN);
        }
    }
}
