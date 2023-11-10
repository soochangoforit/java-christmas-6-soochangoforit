package christmas.model;

import java.util.Set;

public class SpecialDiscount implements DiscountPolicy {
    private static final Set<Integer> SPECIAL_DAYS = Set.of(3, 10, 17, 24, 25, 31);
    private static final int DISCOUNT_AMOUNT = 1000;

    @Override
    public DiscountedAmount applyDiscount(OrderResult orderResult) {
        if (isOrderedInSpecialDay(orderResult)) {
            return DiscountedAmount.from(DISCOUNT_AMOUNT);
        }
        return DiscountedAmount.zero();
    }

    private boolean isOrderedInSpecialDay(OrderResult orderResult) {
        return orderResult.isOrderedIn(SPECIAL_DAYS);
    }
}
