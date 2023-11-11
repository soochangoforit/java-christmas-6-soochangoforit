package christmas.model;

public final class PromotionDiscount implements DiscountPolicy {
    private static final PromotionItem PROMOTION_ITEM = PromotionItem.FREE_CHAMPAGNE;

    @Override
    public DiscountAmounts applyDiscount(OrderInfo orderInfo) {
        if (orderInfo.isQualifiedForPromotion(PROMOTION_ITEM)) {
            int discountedAmount = PROMOTION_ITEM.calculateDiscountedAmount();
            return DiscountAmounts.from(discountedAmount);
        }
        return DiscountAmounts.zero();
    }
}
