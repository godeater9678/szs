package com.szs.domain.user.dto.scrap;

import lombok.Getter;

/*
 * 리뷰어 가독성을 위한 필드 한글 사용
 * */
@Getter
public class IncomeDeduction {
    private String 금액;
    private String 소득구분;
    private String 총납임금액;

}
