package christmas.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum PromotionItem {
    FREE_CHAMPAGNE(Menu.CHAMPAGNE, 120_000, 1);

    private final Menu item;
    private final int minimumAmountForEligibility;
    private final int quantity;

    PromotionItem(Menu item, int minimumAmountForEligibility, int quantity) {
        this.item = item;
        this.minimumAmountForEligibility = minimumAmountForEligibility;
        this.quantity = quantity;
    }

    public static Optional<PromotionItem> findMatchingPromotion(OrderAmounts orderAmounts) {
        return Stream.of(values())
                .filter(promotionItem -> promotionItem.isEligibleFor(orderAmounts))
                .findFirst();
    }

    public boolean isEligibleFor(OrderAmounts orderAmounts) {
        return orderAmounts.isEligibleFor(minimumAmountForEligibility);
    }

    public int calculateDiscountedAmount() {
        int price = item.getPrice();

        return price * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Menu getItem() {
        return item;
    }
}
