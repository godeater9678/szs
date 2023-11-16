package com.szs.domain.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SzsException extends Exception {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public SzsException(String message){
        super(message);
    }
}
