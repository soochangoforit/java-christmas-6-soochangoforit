package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public class AppliedDiscounts {
    private final Map<DiscountType, DiscountedAmount> discountResults;

    private AppliedDiscounts(Map<DiscountType, DiscountedAmount> discountResults) {
        this.discountResults = discountResults;
    }

    public static AppliedDiscounts from(Map<DiscountType, DiscountedAmount> discountResults) {
        return new AppliedDiscounts(new EnumMap<>(discountResults));
    }

    public static AppliedDiscounts empty() {
        return new AppliedDiscounts(new EnumMap<>(DiscountType.class));
    }

    public Map<DiscountType, DiscountedAmount> getDiscountResults() {
        return new EnumMap<>(discountResults);
    }
}
