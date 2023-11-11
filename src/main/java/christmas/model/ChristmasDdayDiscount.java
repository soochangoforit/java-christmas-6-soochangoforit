package christmas.model;

import java.time.LocalDate;

public final class ChristmasDdayDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, 12, 25);

    private static final int START_DISCOUNT = 1000;
    private static final int DAILY_INCREMENT = 100;

    @Override
    public DiscountedAmount applyDiscount(OrderInfo orderInfo) {
        if (isWithinDiscountPeriod(orderInfo)) {
            int daysElapsed = orderInfo.daysSinceStartDate(START_DATE);
            int discountAmount = START_DISCOUNT + (daysElapsed * DAILY_INCREMENT);
            return DiscountedAmount.from(discountAmount);
        }

        return DiscountedAmount.zero();
    }

    private boolean isWithinDiscountPeriod(OrderInfo orderInfo) {
        return orderInfo.isOrderedBetween(START_DATE, END_DATE);
    }
}
