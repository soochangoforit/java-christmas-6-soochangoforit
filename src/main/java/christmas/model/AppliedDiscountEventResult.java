package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public class AppliedDiscountEventResult {
    private final Map<DiscountEventType, DiscountedAmount> discountEventResult;

    private AppliedDiscountEventResult(Map<DiscountEventType, DiscountedAmount> discountEventResult) {
        this.discountEventResult = discountEventResult;
    }

    public static AppliedDiscountEventResult from(Map<DiscountEventType, DiscountedAmount> discountEventResult) {
        return new AppliedDiscountEventResult(new EnumMap<>(discountEventResult));
    }

    public int calculateTotalDiscountedAmount() {
        return discountEventResult.values().stream()
                .mapToInt(DiscountedAmount::getAmount)
                .sum();
    }

    public Map<DiscountEventType, DiscountedAmount> getDiscountEventResult() {
        return new EnumMap<>(discountEventResult);
    }
}
