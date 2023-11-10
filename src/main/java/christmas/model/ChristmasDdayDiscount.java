package christmas.model;

import java.time.LocalDate;

public class ChristmasDdayDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, 12, 25);

    private static final int START_DISCOUNT = 1000;
    private static final int DAILY_INCREMENT = 100;

    @Override
    public DiscountedAmount applyDiscount(OrderResult orderResult) {
        if (isWithinDiscountPeriod(orderResult)) {
            int daysFromStart = orderResult.calculateDaysFrom(START_DATE);
            int discountAmount = START_DISCOUNT + (daysFromStart * DAILY_INCREMENT);
            return DiscountedAmount.from(discountAmount);
        }

        return DiscountedAmount.zero();
    }

    private boolean isWithinDiscountPeriod(OrderResult orderResult) {
        return orderResult.isOrderedBetween(START_DATE, END_DATE);
    }
}
