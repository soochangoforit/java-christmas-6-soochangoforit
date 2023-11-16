package christmas.dto.response;

import christmas.model.TotalBenefitAmounts;

public class TotalBenefitAmountsDto {
    private final int totalBenefitAmounts;

    private TotalBenefitAmountsDto(int totalBenefitAmounts) {
        this.totalBenefitAmounts = totalBenefitAmounts;
    }

    public static TotalBenefitAmountsDto from(TotalBenefitAmounts totalBenefitAmounts) {
        int amounts = totalBenefitAmounts.getAmounts();

        return new TotalBenefitAmountsDto(amounts);
    }

    public int getTotalBenefitAmounts() {
        return totalBenefitAmounts;
    }
}
