package com.szs.domain.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CircuitBreakException extends SzsException {
    public CircuitBreakException(String message){
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
