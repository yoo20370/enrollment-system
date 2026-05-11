package com.github.yoo20370.enrollment.user.service.command;

import com.github.yoo20370.enrollment.user.domain.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateUserCommand {

    private String role;
    private String email;
    private String password;
    private String name;
    private String nickname;

    public static CreateUserCommand of(String role, String email, String password, String name,
        String nickname) {

        return CreateUserCommand.builder()
            .role(role)
            .email(email)
            .password(password)
            .name(name)
            .nickname(nickname)
            .build();
    }
}
