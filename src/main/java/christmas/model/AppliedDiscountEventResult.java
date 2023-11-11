package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public final class AppliedDiscountEventResult {
    private final Map<DiscountEventType, DiscountAmounts> discountEventResult;

    private AppliedDiscountEventResult(Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        this.discountEventResult = new EnumMap<>(discountEventResult);
    }

    public static AppliedDiscountEventResult from(Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        return new AppliedDiscountEventResult(discountEventResult);
    }

    public TotalDiscountAmounts calculateTotalDiscountAmounts() {
        int totalDiscountAmounts = sumDiscountAmounts();

        return TotalDiscountAmounts.from(totalDiscountAmounts);
    }

    private int sumDiscountAmounts() {
        return discountEventResult.values()
                .stream()
                .mapToInt(DiscountAmounts::getAmounts)
                .sum();
    }

    public Map<DiscountEventType, DiscountAmounts> getDiscountEventResult() {
        return new EnumMap<>(discountEventResult);
    }
}
