package christmas.model;

import java.time.LocalDate;
import java.util.Set;

public final class SpecialDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(1);
    private static final LocalDate END_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(31);
    private static final Set<Integer> SPECIAL_DAYS_FOR_DISCOUNT = Set.of(3, 10, 17, 24, 25, 31);
    private static final int DISCOUNT_AMOUNT = 1_000;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isOrderedInEventPeriod(orderInfo) && isOrderedInSpecialDay(orderInfo)) {
            return DiscountAmounts.from(DISCOUNT_AMOUNT);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isOrderedInEventPeriod(OrderInfo orderInfo) {
        return orderInfo.isOrderedBetween(START_DATE, END_DATE);
    }

    private boolean isOrderedInSpecialDay(OrderInfo orderInfo) {
        return orderInfo.isOrderedInDays(SPECIAL_DAYS_FOR_DISCOUNT);
    }
}
