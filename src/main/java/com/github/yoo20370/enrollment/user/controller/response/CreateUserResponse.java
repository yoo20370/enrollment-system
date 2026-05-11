package com.github.yoo20370.enrollment.user.controller.response;

import com.github.yoo20370.enrollment.user.domain.ActiveStatus;
import com.github.yoo20370.enrollment.user.domain.UserRole;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateUserResponse {
    private final UUID id;

    private final String role;

    private final String email;

    private final String name;

    private final String nickName;

    private final String activeStatus;

    public static CreateUserResponse of(UUID id, UserRole role, String email, String name,
        String nickName, ActiveStatus activeStatus) {
        return CreateUserResponse.builder()
            .id(id)
            .role(role.name())
            .email(email)
            .name(name)
            .nickName(nickName)
            .activeStatus(activeStatus.name())
            .build();
    }

}
