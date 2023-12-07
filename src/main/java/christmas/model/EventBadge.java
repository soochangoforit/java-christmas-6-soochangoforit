package christmas.model;

import java.util.Comparator;
import java.util.stream.Stream;

public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("없음", 0);

    private final String name;
    private final int minimumOrderAmount;

    EventBadge(String name, int minimumOrderAmount) {
        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public static EventBadge findEventBadge(BenefitAmounts totalBenefitAmounts) {
        return Stream.of(EventBadge.values())
                .sorted(Comparator.comparing(EventBadge::getMinimumOrderAmount).reversed())
                .filter(eventBadge -> totalBenefitAmounts.isGreaterThanOrEqualTo(eventBadge.minimumOrderAmount))
                .findFirst()
                .orElse(NONE);
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public String getName() {
        return name;
    }
}
