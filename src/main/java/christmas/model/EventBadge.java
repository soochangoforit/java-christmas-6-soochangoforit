package christmas.model;

import java.util.stream.Stream;

public enum EventBadge {
    NONE("없음", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String name;
    private final int eligiblePrice;

    EventBadge(String name, int eligiblePrice) {
        this.name = name;
        this.eligiblePrice = eligiblePrice;
    }

    public static EventBadge findMatchingEventBadge(int totalDiscountedAmount) {
        return Stream.of(values())
                .filter(eventBadge -> eventBadge.isEligible(totalDiscountedAmount))
                .findFirst()
                .orElse(NONE);
    }

    private boolean isEligible(int totalDiscountedAmount) {
        return totalDiscountedAmount >= eligiblePrice;
    }

    public String getName() {
        return name;
    }
}
