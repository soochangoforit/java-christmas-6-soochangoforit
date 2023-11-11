package christmas.model;

import java.time.LocalDate;

public final class ChristmasDdayDiscount implements DiscountPolicy {
    private static final int EVENT_YEAR = EventSchedule.MAIN_EVENT_SEASON.getYear();
    private static final int EVENT_MONTH = EventSchedule.MAIN_EVENT_SEASON.getMonth();
    private static final int EVENT_START_DAY = 1;
    private static final int EVENT_END_DAY = 25;
    private static final LocalDate START_DATE = LocalDate.of(EVENT_YEAR, EVENT_MONTH, EVENT_START_DAY);
    private static final LocalDate END_DATE = LocalDate.of(EVENT_YEAR, EVENT_MONTH, EVENT_END_DAY);
    private static final int START_DISCOUNT = 1000;
    private static final int DAILY_INCREMENT = 100;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isWithinDiscountPeriod(orderInfo)) {
            int daysElapsed = orderInfo.daysSinceStartDate(START_DATE);
            int discountAmounts = calculateDiscountAmount(daysElapsed);

            return DiscountAmounts.from(discountAmounts);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isWithinDiscountPeriod(OrderInfo orderInfo) {
        return orderInfo.isOrderedBetween(START_DATE, END_DATE);
    }

    private int calculateDiscountAmount(int daysElapsed) {
        return START_DISCOUNT + (daysElapsed * DAILY_INCREMENT);
    }

}
