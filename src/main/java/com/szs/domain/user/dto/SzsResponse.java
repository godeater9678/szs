package com.szs.domain.user.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public class SzsResponse<T> implements Serializable {
    int status;
    T data;
    String message;
    public SzsResponse(HttpStatus httpStatus, T data){
        this.status = httpStatus.value();
        this.data = data;
    }
    public SzsResponse(HttpStatus httpStatus, T data, String message){
        this.status = httpStatus.value();
        this.data = data;
        this.message = message;
    }
    public SzsResponse(HttpStatus httpStatus, String message){
        this.status = httpStatus.value();
        this.message = message;
    }
}
