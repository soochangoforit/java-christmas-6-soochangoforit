package christmas.model;

import java.util.stream.Stream;

public enum EventBadge {
    NONE("없음", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

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
