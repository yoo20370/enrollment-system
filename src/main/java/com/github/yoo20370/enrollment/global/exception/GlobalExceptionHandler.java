package com.github.yoo20370.enrollment.global.exception;


import com.github.yoo20370.enrollment.global.common.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(ApiResponse.fail(ErrorResponse.of(errorCode)));
    }

    // @Valid 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
            .getFieldErrors()
            .get(0)
            .getDefaultMessage();

        return ResponseEntity
            .status(400)
            .body(ApiResponse.fail(ErrorResponse.of(ErrorCode.COMMON_INVALID_INPUT, message)));
    }

    // @RequestParam, @PathVariable 검증 실패
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleConstraintViolationException(
        ConstraintViolationException e){
        return ResponseEntity
            .status(400)
            .body(ApiResponse.fail(ErrorResponse.of(ErrorCode.COMMON_INVALID_INPUT, e.getMessage())));
    }

    // 헤드 누락 시 처리
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMissingRequestHeader(MissingRequestHeaderException e) {
        return ResponseEntity.status(400)
            .body(ApiResponse.fail(ErrorResponse.of(ErrorCode.COMMON_INVALID_INPUT, e.getHeaderName() + " 헤더가 필요합니다.")));
    }

    // Enum에 해당하지 않는 값이 입력값으로 들어오는 경우 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(400)
            .body(ApiResponse.fail(ErrorResponse.of(ErrorCode.COMMON_INVALID_INPUT)));
    }

    // 그 외 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception e) {
        return ResponseEntity
            .status(500)
            .body(ApiResponse.fail(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)));
    }

}
