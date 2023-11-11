package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public class AppliedDiscountEventResult {
    private final Map<DiscountEventType, DiscountAmounts> discountEventResult;

    private AppliedDiscountEventResult(Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        this.discountEventResult = discountEventResult;
    }

    public static AppliedDiscountEventResult from(Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        return new AppliedDiscountEventResult(new EnumMap<>(discountEventResult));
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
