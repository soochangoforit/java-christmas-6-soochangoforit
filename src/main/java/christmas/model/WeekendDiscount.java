package christmas.model;

import java.time.DayOfWeek;
import java.util.EnumSet;

public class WeekendDiscount implements DiscountPolicy {
    private static final int DISCOUNT_PER_MAIN = 2023;
    private static final EnumSet<DayOfWeek> WEEKEND_DAYS = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    private static final Category MAIN_CATEGORY = Category.MAIN;

    @Override
    public DiscountedAmount applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInWeekend(orderInfo)) {
            int totalMainCount = orderInfo.totalMenuQuantityOfCategory(MAIN_CATEGORY);
            int discountAmount = totalMainCount * DISCOUNT_PER_MAIN;
            return DiscountedAmount.from(discountAmount);
        }

        return DiscountedAmount.zero();
    }

    private boolean isOrderedInWeekend(OrderInfo orderInfo) {
        return orderInfo.isOrderedIn(WEEKEND_DAYS);
    }
}
