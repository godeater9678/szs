package com.szs.domain.user.handler;

import com.szs.domain.user.dto.SzsResponse;
import com.szs.domain.user.exception.SzsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SzsException.class)
    public ResponseEntity<SzsResponse> handleSzsException(SzsException ex, WebRequest request) {
        // 예외 처리 로직을 여기에 작성합니다.
        SzsResponse<Object> szsResponse = new SzsResponse<>(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(szsResponse, ex.getStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SzsResponse> handleGlobalException(Exception ex, WebRequest request) {
        // 예외 처리 로직을 여기에 작성합니다.
        SzsResponse<Object> szsResponse = new SzsResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        System.out.println(ex.getStackTrace());
        return new ResponseEntity<>(szsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 다른 예외 핸들러 메서드를 추가할 수 있습니다.
}