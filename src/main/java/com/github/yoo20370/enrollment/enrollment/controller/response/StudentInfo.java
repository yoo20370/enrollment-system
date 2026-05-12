package com.github.yoo20370.enrollment.enrollment.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class StudentInfo {

    private final String userId;
    private final String name;
    private final String email;

    public static StudentInfo of(
        String userId,
        String name,
        String email
    ) {
        return StudentInfo.builder()
            .userId(userId)
            .name(name)
            .email(email)
            .build();
    }

}
