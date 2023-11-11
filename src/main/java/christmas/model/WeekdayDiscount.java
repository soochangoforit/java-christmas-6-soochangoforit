package christmas.model;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

public final class WeekdayDiscount implements DiscountPolicy {
    private static final Set<DayOfWeek> DISCOUNT_DAYS_OF_WEEK = EnumSet.of(
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
    private static final int DISCOUNT_AMOUNT_FOR_EACH_DESSERT = 2023;
    private static final Category DESSERT_CATEGORY = Category.DESSERT;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInWeekday(orderInfo)) {
            int totalDessertQuantity = orderInfo.sumTotalOrderItemQuantityIn(DESSERT_CATEGORY);
            int discountAmounts = calculateDiscountAmounts(totalDessertQuantity);

            return DiscountAmounts.from(discountAmounts);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isOrderedInWeekday(OrderInfo orderInfo) {
        return orderInfo.isOrderedInDaysOfWeek(DISCOUNT_DAYS_OF_WEEK);
    }

    private int calculateDiscountAmounts(int totalDessertQuantity) {
        return totalDessertQuantity * DISCOUNT_AMOUNT_FOR_EACH_DESSERT;
    }

}
