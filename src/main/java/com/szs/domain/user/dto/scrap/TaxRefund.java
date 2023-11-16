package com.szs.domain.user.dto.scrap;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class TaxRefund {
    String 산출세액;
    String 근로소득세액공제금액;
    String 퇴직연금세액공제금액;
    String 특별세액공제금액;
    String 표준세액공제금액;
    String 결정세액;
}
