package christmas.model;

import java.time.DayOfWeek;
import java.util.EnumSet;

public class WeekdayDiscount implements DiscountPolicy {
    private static final int DISCOUNT_PER_DESSERT = 2023;
    private static final Category DESSERT_CATEGORY = Category.DESSERT;

    @Override
    public DiscountedAmount applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInWeekday(orderInfo)) {
            int totalDessertCount = orderInfo.totalMenuQuantityOfCategory(DESSERT_CATEGORY);
            int discountAmount = totalDessertCount * DISCOUNT_PER_DESSERT;
            return DiscountedAmount.from(discountAmount);
        }

        return DiscountedAmount.zero();
    }

    private boolean isOrderedInWeekday(OrderInfo orderInfo) {
        return orderInfo.isOrderedIn(
                EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY));
    }
}
