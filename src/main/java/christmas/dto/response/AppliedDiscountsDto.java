package christmas.dto.response;

import java.util.EnumMap;
import java.util.Map;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountAmounts;
import christmas.model.DiscountEventType;

public class AppliedDiscountsDto {
    private final Map<DiscountEventType, Integer> appliedDiscounts;

    private AppliedDiscountsDto(Map<DiscountEventType, Integer> appliedDiscounts) {
        this.appliedDiscounts = appliedDiscounts;
    }

    public static AppliedDiscountsDto from(AppliedDiscountEventResult appliedDiscountEventResult) {
        Map<DiscountEventType, DiscountAmounts> discountResults = appliedDiscountEventResult.getDiscountEventResult();
        Map<DiscountEventType, Integer> discountAmounts = new EnumMap<>(DiscountEventType.class);

        discountResults.forEach((discountType, discountedAmount) -> {
            if (discountedAmount.getAmounts() > 0) {
                discountAmounts.put(discountType, discountedAmount.getAmounts());
            }
        });

        return new AppliedDiscountsDto(discountAmounts);
    }

    public Map<DiscountEventType, Integer> getAppliedDiscounts() {
        return appliedDiscounts;
    }
}
