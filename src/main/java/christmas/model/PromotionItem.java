package christmas.model;

import java.util.Comparator;
import java.util.stream.Stream;

public enum PromotionItem {
    FREE_CHAMPAGNE(120_000, 1, Menu.CHAMPAGNE),
    NONE(0, 0, Menu.NONE);

    private final int minimumOrderAmount;
    private final int quantity;
    private final Menu item;

    PromotionItem(int minimumOrderAmount, int quantity, Menu item) {
        this.minimumOrderAmount = minimumOrderAmount;
        this.quantity = quantity;
        this.item = item;
    }

    public static PromotionItem determinePromotionItem(OrderAmounts orderAmountsBeforeDiscount) {
        return Stream.of(PromotionItem.values())
                .sorted(Comparator.comparing(PromotionItem::getMinimumOrderAmount).reversed())
                .filter(promotionItem -> orderAmountsBeforeDiscount.isGreaterThanOrEqualTo(
                        promotionItem.getMinimumOrderAmount()))
                .findFirst()
                .orElse(NONE);
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public boolean isEligible(OrderAmounts orderAmountsBeforeDiscount) {
        return orderAmountsBeforeDiscount.isGreaterThanOrEqualTo(minimumOrderAmount);
    }

    public int calculateBenefitAmounts() {
        return item.calculateAmount(quantity);
    }

    public boolean isNone() {
        return this == NONE;
    }

    public Menu getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
