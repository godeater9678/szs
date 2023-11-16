package com.szs.domain.user.dto.scrap;

import com.szs.domain.user.dto.scrap.ApiData;
import lombok.Getter;

@Getter
public class ScrapResponse {
    private String status;
    private ApiData data;
    private Object errors;

}

