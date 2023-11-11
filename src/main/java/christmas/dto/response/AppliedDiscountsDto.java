package christmas.dto.response;

import java.util.EnumMap;
import java.util.Map;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountEventType;
import christmas.model.DiscountedAmount;

public class AppliedDiscountsDto {
    private final Map<DiscountEventType, Integer> appliedDiscounts;

    private AppliedDiscountsDto(Map<DiscountEventType, Integer> appliedDiscounts) {
        this.appliedDiscounts = appliedDiscounts;
    }

    public static AppliedDiscountsDto from(AppliedDiscountEventResult appliedDiscountEventResult) {
        Map<DiscountEventType, DiscountedAmount> discountResults = appliedDiscountEventResult.getDiscountEventResult();
        Map<DiscountEventType, Integer> discountAmounts = new EnumMap<>(DiscountEventType.class);

        discountResults.forEach((discountType, discountedAmount) -> {
            if (discountedAmount.getAmount() > 0) {
                discountAmounts.put(discountType, discountedAmount.getAmount());
            }
        });

        return new AppliedDiscountsDto(discountAmounts);
    }

    public Map<DiscountEventType, Integer> getAppliedDiscounts() {
        return appliedDiscounts;
    }
}
