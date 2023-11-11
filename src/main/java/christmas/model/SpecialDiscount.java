package christmas.model;

import java.util.Set;

public final class SpecialDiscount implements DiscountPolicy {
    private static final Set<Integer> SPECIAL_DAYS = Set.of(3, 10, 17, 24, 25, 31);
    private static final int DISCOUNT_AMOUNT = 1000;

    @Override
    public DiscountedAmount applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInSpecialDay(orderInfo)) {
            return DiscountedAmount.from(DISCOUNT_AMOUNT);
        }
        return DiscountedAmount.zero();
    }

    private boolean isOrderedInSpecialDay(OrderInfo orderInfo) {
        return orderInfo.isOrderedIn(SPECIAL_DAYS);
    }
}
