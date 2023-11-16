package com.szs.domain.user.dto.scrap;

import lombok.Getter;

@Getter
public class ApiData {
    private JsonList jsonList;
    private String appVer;
    private String errMsg;
    private String company;
    private String svcCd;
    private String hostNm;
    private String workerResDt;
    private String workerReqDt;

}
