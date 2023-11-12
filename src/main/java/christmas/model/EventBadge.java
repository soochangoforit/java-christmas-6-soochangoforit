package christmas.model;

import java.util.stream.Stream;

public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("없음", 0);

    private static final String BLANK_NAME = "이벤트 배지 이름은 공백일 수 없습니다.";
    private static final String INVALID_MIN_AMOUNT_FOR_ELIGIBILITY = "이벤트 배지 최소 금액은 %d원 이상이어야 합니다.";
    private static final int MIN_AMOUNT_FOR_ELIGIBILITY = 0;

    private final String name;
    private final int minimumAmountForEligibility;

    EventBadge(String name, int minimumAmountForEligibility) {
        validate(name, minimumAmountForEligibility);
        this.name = name;
        this.minimumAmountForEligibility = minimumAmountForEligibility;
    }

    private void validate(String name, int minimumAmountForEligibility) {
        validateBlank(name);
        validateMinimumAmount(minimumAmountForEligibility);
    }

    private void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(BLANK_NAME);
        }
    }

    private void validateMinimumAmount(int minimumAmountForEligibility) {
        if (minimumAmountForEligibility < MIN_AMOUNT_FOR_ELIGIBILITY) {
            String exceptionMessage = String.format(INVALID_MIN_AMOUNT_FOR_ELIGIBILITY, MIN_AMOUNT_FOR_ELIGIBILITY);
            throw new IllegalArgumentException(exceptionMessage);
        }
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
