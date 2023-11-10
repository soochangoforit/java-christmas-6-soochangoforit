package christmas.model;

import java.time.DayOfWeek;
import java.util.EnumSet;

public class WeekdayDiscount implements DiscountPolicy {
    private static final int DISCOUNT_PER_DESSERT = 2023;
    private static final Category DESSERT_CATEGORY = Category.DESSERT;

    @Override
    public DiscountedAmount applyDiscount(OrderResult orderResult) {
        if (isOrderedInWeekday(orderResult)) {
            int totalDessertCount = orderResult.countOrdersIn(DESSERT_CATEGORY);
            int discountAmount = totalDessertCount * DISCOUNT_PER_DESSERT;
            return DiscountedAmount.from(discountAmount);
        }

        return DiscountedAmount.zero();
    }

    private boolean isOrderedInWeekday(OrderResult orderResult) {
        return orderResult.isOrderedIn(
                EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY));
    }
}
