package christmas.model;

import java.util.Set;

public final class SpecialDiscount implements DiscountPolicy {
    private static final Set<Integer> SPECIAL_DAYS_FOR_DISCOUNT = Set.of(3, 10, 17, 24, 25, 31);
    private static final int DISCOUNT_AMOUNT = 1_000;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInSpecialDay(orderInfo)) {
            return DiscountAmounts.from(DISCOUNT_AMOUNT);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isOrderedInSpecialDay(OrderInfo orderInfo) {
        return orderInfo.isOrderedInDays(SPECIAL_DAYS_FOR_DISCOUNT);
    }
}
