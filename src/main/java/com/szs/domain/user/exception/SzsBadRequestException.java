package com.szs.domain.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SzsBadRequestException extends SzsException {
    public SzsBadRequestException(String message){
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
