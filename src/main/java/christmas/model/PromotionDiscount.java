package christmas.model;

public class PromotionDiscount implements DiscountPolicy {
    private static final PromotionItem PROMOTION_ITEM = PromotionItem.FREE_CHAMPAGNE;

    @Override
    public DiscountedAmount applyDiscount(OrderResult orderResult) {
        if (orderResult.isQualifiedForPromotion(PROMOTION_ITEM)) {
            int discountedAmount = PROMOTION_ITEM.calculateDiscountedAmount();
            return DiscountedAmount.from(discountedAmount);
        }
        return DiscountedAmount.zero();
    }
}
