package com.szs.domain.user.helper;

import com.szs.domain.user.dto.scrap.IncomeDeduction;
import com.szs.domain.user.dto.scrap.IncomeItem;
import com.szs.domain.user.dto.scrap.ScrapResponse;
import com.szs.domain.user.dto.scrap.TaxRefund;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

/*
* API 스펙이 한글이어서 편의상 한글변수를 사용했습니다. (실무x)
* */
public class TaxCalculator {

    public static TaxRefund calculateDecisionTax(ScrapResponse scrapResponse) {

        double sanchulTax = Double.parseDouble(scrapResponse.getData().getJsonList().get산출세액().replaceAll(",", ""));
        double workTax = sanchulTax * 0.55;
        double byeTax = calculateRetirementPensionTax(scrapResponse);
        double specialTax = calculateSpecialTaxDeduction(scrapResponse);
        double standardTax = calculateStandardTaxDeduction(specialTax);

        double refund = sanchulTax - workTax - specialTax - standardTax - byeTax;
        return TaxRefund.builder()
                .산출세액(formatCurrency(sanchulTax))
                .근로소득세액공제금액(formatCurrency(workTax))
                .퇴직연금세액공제금액(formatCurrency(byeTax))
                .특별세액공제금액(formatCurrency(specialTax))
                .표준세액공제금액(formatCurrency(standardTax))
                .결정세액(formatCurrency(refund))
                .build();
    }

    public static String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(amount);
    }

    //소등공제 리스트에서 지정 과목의 금액을 반환한다.
    private static double getPriceByTypeInJsonList(ScrapResponse scrapResponse, String fieldName){
        List<IncomeDeduction> found = scrapResponse.getData().getJsonList().get소득공제().stream().filter(x->x.get소득구분().equals(fieldName)).collect(Collectors.toList());
        if(found.isEmpty()){
            return 0;
        }
        if(found.get(0).get금액() != null) {
            return Double.parseDouble(found.get(0).get금액().replaceAll(",", ""));
        }else if(found.get(0).get총납임금액() != null) {
            return Double.parseDouble(found.get(0).get총납임금액().replaceAll(",", ""));
        }
        return 0;
    }

    private static double calculateRetirementPensionTax(ScrapResponse scrapResponse) {
        double byeFund = getPriceByTypeInJsonList(scrapResponse, "퇴직연금");
        return byeFund * 0.15;
    }

    //특별세액공제금액
    private static double calculateSpecialTaxDeduction(ScrapResponse scrapResponse) {
        double bo = getPriceByTypeInJsonList(scrapResponse, "보험료") * 0.12;
        double medi = calculateMedicalExpenseDeduction(scrapResponse);
        double edu = getPriceByTypeInJsonList(scrapResponse, "교육비") * 0.15;
        double dona = getPriceByTypeInJsonList(scrapResponse, "기부금") * 0.15;

        return bo + medi + edu + dona;
    }


    //의료비공제금액
    private static double calculateMedicalExpenseDeduction(ScrapResponse scrapResponse) {
        double medi = getPriceByTypeInJsonList(scrapResponse, "의료비");
        double salary = 0;
        for(IncomeItem incomeItem : scrapResponse.getData().getJsonList().get급여()){
            salary += Double.parseDouble(incomeItem.get총지급액().replaceAll(",", ""));
        }

        double mediFee = (medi - (salary * 0.03)) * 0.15;

        if (mediFee < 0) {
            mediFee = 0;
        }

        return mediFee;
    }

    private static double calculateStandardTaxDeduction(double special) {
        if (special < 130000) {
            return 130000;
        } else {
            return special;
        }
    }
}