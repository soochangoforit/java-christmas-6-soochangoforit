package christmas.dto.response;

import java.util.EnumMap;
import java.util.Map;
import christmas.model.AppliedDiscounts;
import christmas.model.DiscountType;
import christmas.model.DiscountedAmount;

public class AppliedDiscountsDto {
    private final Map<DiscountType, Integer> appliedDiscounts;

    private AppliedDiscountsDto(Map<DiscountType, Integer> appliedDiscounts) {
        this.appliedDiscounts = appliedDiscounts;
    }

    public static AppliedDiscountsDto from(AppliedDiscounts appliedDiscounts) {
        Map<DiscountType, DiscountedAmount> discountResults = appliedDiscounts.getDiscountResults();
        Map<DiscountType, Integer> discountAmounts = new EnumMap<>(DiscountType.class);

        discountResults.forEach((discountType, discountedAmount) -> {
            if (discountedAmount.getAmount() > 0) {
                discountAmounts.put(discountType, discountedAmount.getAmount());
            }
        });

        return new AppliedDiscountsDto(discountAmounts);
    }

    public Map<DiscountType, Integer> getAppliedDiscounts() {
        return appliedDiscounts;
    }
}
