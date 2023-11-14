package christmas.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public final class AppliedDiscountEventResult {
    private final Map<DiscountEventType, DiscountAmounts> discountEventResult;

    private AppliedDiscountEventResult(Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        this.discountEventResult = new EnumMap<>(discountEventResult);
    }

    public static AppliedDiscountEventResult from(Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        return new AppliedDiscountEventResult(discountEventResult);
    }

    public TotalBenefitAmounts calculateTotalBenefitAmounts() {
        int totalBenefitAmounts = sumTotalBenefitAmounts();

        return TotalBenefitAmounts.from(totalBenefitAmounts);
    }

    private int sumTotalBenefitAmounts() {
        return discountEventResult.values()
                .stream()
                .mapToInt(DiscountAmounts::getAmounts)
                .sum();
    }

    public DiscountAmounts calculateTotalDiscountAmounts() {
        int totalDiscountAmounts = sumTotalDiscountAmounts();

        return DiscountAmounts.from(totalDiscountAmounts);
    }

    private int sumTotalDiscountAmounts() {
        return discountEventResult.entrySet()
                .stream()
                .filter(isDiscountEvent())
                .mapToInt(getDiscountAmounts())
                .sum();
    }

    private Predicate<Entry<DiscountEventType, DiscountAmounts>> isDiscountEvent() {
        return entry -> entry.getKey().isDiscountEvent();
    }

    private ToIntFunction<Entry<DiscountEventType, DiscountAmounts>> getDiscountAmounts() {
        return entry -> entry.getValue().getAmounts();
    }

    public Map<DiscountEventType, DiscountAmounts> getDiscountEventResult() {
        return new EnumMap<>(discountEventResult);
    }
}
