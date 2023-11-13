package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public final class WeekdayDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(1);
    private static final LocalDate END_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(31);
    private static final Set<DayOfWeek> DISCOUNT_DAYS_OF_WEEK = EnumSet.of(
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
    private static final int DISCOUNT_AMOUNT_FOR_EACH_DESSERT = 2_023;
    private static final Category DESSERT_CATEGORY = Category.DESSERT;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInEventPeriod(orderInfo) && isOrderedInWeekday(orderInfo)) {
            int totalDessertQuantity = orderInfo.sumTotalOrderItemQuantityIn(DESSERT_CATEGORY);
            int discountAmounts = calculateDiscountAmounts(totalDessertQuantity);

            return DiscountAmounts.from(discountAmounts);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isOrderedInEventPeriod(OrderInfo orderInfo) {
        return orderInfo.isOrderedBetween(START_DATE, END_DATE);
    }

    private boolean isOrderedInWeekday(OrderInfo orderInfo) {
        return orderInfo.isOrderedInDaysOfWeek(DISCOUNT_DAYS_OF_WEEK);
    }

    private int calculateDiscountAmounts(int totalDessertQuantity) {
        return totalDessertQuantity * DISCOUNT_AMOUNT_FOR_EACH_DESSERT;
    }

}
