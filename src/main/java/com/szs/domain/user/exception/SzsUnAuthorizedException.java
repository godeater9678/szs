package com.szs.domain.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SzsUnAuthorizedException extends SzsException {
    public SzsUnAuthorizedException(String message){
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
    
}
