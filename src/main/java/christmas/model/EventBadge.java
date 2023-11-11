package christmas.model;

import java.util.stream.Stream;

public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("없음", 0);

    private final String name;
    private final int minimumAmountForEligibility;

    EventBadge(String name, int minimumAmountForEligibility) {
        this.name = name;
        this.minimumAmountForEligibility = minimumAmountForEligibility;
    }

    public static EventBadge findMatchingEventBadge(TotalDiscountAmounts totalDiscountedAmount) {
        return Stream.of(values())
                .filter(eventBadge -> eventBadge.isEligibleFor(totalDiscountedAmount))
                .findFirst()
                .orElse(NONE);
    }

    private boolean isEligibleFor(TotalDiscountAmounts totalDiscountedAmount) {
        return totalDiscountedAmount.isEligibleFor(minimumAmountForEligibility);
    }

    public String getName() {
        return name;
    }
}
