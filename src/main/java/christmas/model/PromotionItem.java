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

    public static Optional<PromotionItem> findMatchingPromotion(int totalPrice) {
        return Stream.of(values())
                .filter(promotionItem -> promotionItem.isEligible(totalPrice))
                .findFirst();
    }

    private boolean isEligible(int totalPrice) {
        return totalPrice >= minimumAmountForEligibility;
    }

    public int getQuantity() {
        return quantity;
    }

    public Menu getItem() {
        return item;
    }
}
