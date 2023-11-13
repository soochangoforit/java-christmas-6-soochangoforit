package christmas.model;

import java.time.LocalDate;

public final class PromotionDiscount implements DiscountPolicy {
    private static final LocalDate START_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(1);
    private static final LocalDate END_DATE = EventSchedule.MAIN_EVENT_SEASON.createDateForDay(31);
    private static final PromotionItem PROMOTION_ITEM = PromotionItem.FREE_CHAMPAGNE;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isEligibleForPromotion(orderInfo) && orderInfo.isOrderedBetween(START_DATE, END_DATE)) {
            int discountAmounts = PROMOTION_ITEM.calculateDiscountAmounts();
            return DiscountAmounts.from(discountAmounts);
        }

        return DiscountAmounts.noDiscount();
    }

    private boolean isEligibleForPromotion(OrderInfo orderInfo) {
        return orderInfo.isEligibleFor(PROMOTION_ITEM);
    }
}
