package christmas.model;

import java.time.DayOfWeek;
import java.util.EnumSet;

public final class WeekendDiscount implements DiscountPolicy {
    private static final int DISCOUNT_PER_MAIN = 2023;
    private static final EnumSet<DayOfWeek> WEEKEND_DAYS = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    private static final Category MAIN_CATEGORY = Category.MAIN;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInWeekend(orderInfo)) {
            int totalMainCount = orderInfo.sumTotalOrderItemQuantity(MAIN_CATEGORY);
            int discountAmount = totalMainCount * DISCOUNT_PER_MAIN;
            return DiscountAmounts.from(discountAmount);
        }

        return DiscountAmounts.zero();
    }

    private boolean isOrderedInWeekend(OrderInfo orderInfo) {
        return orderInfo.isOrderedIn(WEEKEND_DAYS);
    }
}
