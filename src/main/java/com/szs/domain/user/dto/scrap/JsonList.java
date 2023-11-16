package com.szs.domain.user.dto.scrap;

import lombok.Getter;

import java.util.List;

/*
 * 리뷰어 가독성을 위한 필드 한글 사용
 * */
@Getter
public class JsonList {
    private List<IncomeItem> 급여;
    private String 산출세액;
    private List<IncomeDeduction> 소득공제;

}
