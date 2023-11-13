package christmas.model;

import java.time.LocalDate;

public final class ChristmasDdayDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = EventSchedule.MAIN_EVENT_SEASON.getStartDate(1);
    private static final LocalDate END_DATE = EventSchedule.MAIN_EVENT_SEASON.getEndDate(25);
    private static final int START_DISCOUNT = 1000;
    private static final int DAILY_INCREMENT = 100;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (orderInfo.isOrderedBetween(START_DATE, END_DATE)) {
            int daysElapsed = orderInfo.daysSinceStartDate(START_DATE);
            int discountAmounts = calculateDiscountAmount(daysElapsed);

            return DiscountAmounts.from(discountAmounts);
        }

        return DiscountAmounts.noDiscount();
    }

    private int calculateDiscountAmount(int daysElapsed) {
        return START_DISCOUNT + (daysElapsed * DAILY_INCREMENT);
    }

}
