package christmas.dto.response;

import christmas.model.TotalDiscountAmounts;

public class TotalDiscountAmountsDto {
    private final int totalDiscountAmounts;

    private TotalDiscountAmountsDto(int totalDiscountAmounts) {
        this.totalDiscountAmounts = totalDiscountAmounts;
    }

    public static TotalDiscountAmountsDto from(TotalDiscountAmounts totalDiscountAmounts) {
        int amounts = totalDiscountAmounts.getAmounts();

        return new TotalDiscountAmountsDto(amounts);
    }

    public int getTotalDiscountAmounts() {
        return totalDiscountAmounts;
    }
}
