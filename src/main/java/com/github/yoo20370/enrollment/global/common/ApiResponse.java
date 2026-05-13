package com.github.yoo20370.enrollment.global.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private final boolean success;
    private final T data;

    protected ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> fail(T data) {
        return new ApiResponse<>(false, data);
    }
}
