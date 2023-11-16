package com.szs.domain.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ScrapResponse {
    private String status;
    private ApiData data;
    private Object errors;

}

@Getter
class ApiData {
    private JsonList jsonList;
    private String appVer;
    private String errMsg;
    private String company;
    private String svcCd;
    private String hostNm;
    private String workerResDt;
    private String workerReqDt;

}

/*
* 리뷰어 가독성을 위한 필드 한글 사용
* */
@Getter
class JsonList {
    private List<IncomeItem> 급여;
    private String 산출세액;
    private List<IncomeDeduction> 소득공제;

}
/*
 * 리뷰어 가독성을 위한 필드 한글 사용
 * */
@Getter
class IncomeItem {
    private String 소득내역;
    private String 총지급액;
    private String 업무시작일;
    private String 기업명;
    private String 이름;
    private String 지급일;
    private String 업무종료일;
    private String 주민등록번호;
    private String 소득구분;
    private String 사업자등록번호;

}
/*
 * 리뷰어 가독성을 위한 필드 한글 사용
 * */
@Getter
class IncomeDeduction {
    private String 금액;
    private String 소득구분;
    private String 총납임금액;

}
