package christmas.model;

import java.util.stream.Stream;

public enum PromotionItem {
    FREE_CHAMPAGNE(Menu.CHAMPAGNE, 120_000, 1),
    NONE(Menu.NONE, 0, 0);

    private final Menu item;
    private final int minimumAmountForEligibility;
    private final int quantity;

    PromotionItem(Menu item, int minimumAmountForEligibility, int quantity) {
        this.item = item;
        this.minimumAmountForEligibility = minimumAmountForEligibility;
        this.quantity = quantity;
    }

    public static PromotionItem findMatchingPromotion(OrderAmounts orderAmounts) {
        return Stream.of(values())
                .filter(promotionItem -> promotionItem.isEligibleFor(orderAmounts))
                .findFirst()
                .orElse(NONE);
    }

    public boolean isEligibleFor(OrderAmounts orderAmounts) {
        return orderAmounts.isEligibleFor(minimumAmountForEligibility);
    }

    public int calculateDiscountAmounts() {
        return item.calculateDiscountAmountsForPromotion(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public Menu getItem() {
        return item;
    }
}
