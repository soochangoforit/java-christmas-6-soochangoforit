package christmas.model;

public final class PromotionDiscount implements DiscountPolicy {
    private static final PromotionItem PROMOTION_ITEM = PromotionItem.FREE_CHAMPAGNE;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (isEligibleForPromotion(orderInfo)) {
            int discountAmounts = PROMOTION_ITEM.calculateTotalPrice();
            return DiscountAmounts.from(discountAmounts);
        }
        return DiscountAmounts.noDiscount();
    }

    private boolean isEligibleForPromotion(OrderInfo orderInfo) {
        return orderInfo.isEligibleFor(PROMOTION_ITEM);
    }
}
