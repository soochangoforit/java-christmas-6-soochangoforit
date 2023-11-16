package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public final class WeekendDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(1);
    private static final LocalDate END_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(31);
    private static final Set<DayOfWeek> DISCOUNT_DAYS_OF_WEEK = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    private static final int DISCOUNT_AMOUNT_FOR_EACH_MAIN = 2_023;
    private static final Category MAIN_CATEGORY = Category.MAIN;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInEventPeriod(orderInfo) && isOrderedInWeekend(orderInfo)) {
            int totalMainQuantity = orderInfo.sumTotalOrderItemQuantityIn(MAIN_CATEGORY);
            int discountAmounts = calculateDiscountAmounts(totalMainQuantity);

            return DiscountAmounts.from(discountAmounts);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isOrderedInEventPeriod(OrderInfo orderInfo) {
        return orderInfo.isOrderedBetween(START_DATE, END_DATE);
    }

    private int calculateDiscountAmounts(int totalMainQuantity) {
        return totalMainQuantity * DISCOUNT_AMOUNT_FOR_EACH_MAIN;
    }

    private boolean isOrderedInWeekend(OrderInfo orderInfo) {
        return orderInfo.isOrderedInDaysOfWeek(DISCOUNT_DAYS_OF_WEEK);
    }
}
