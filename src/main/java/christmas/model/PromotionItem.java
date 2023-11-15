package christmas.model;

import java.util.stream.Stream;

public enum PromotionItem {
    FREE_CHAMPAGNE(Menu.CHAMPAGNE, 120_000, 1),
    NONE(Menu.NONE, 0, 0);

    private static final String INVALID_AMOUNTS = "[ERROR] 증정 상품을 받기 위한 최소 주문 금액은 %d원 이상이어야 합니다.";
    private static final String INVALID__QUANTITY = "[ERROR] 증정 상품의 최소 수량은 %d개 이상이어야 합니다.";
    private static final int ZERO = 0;

    private final Menu item;
    private final int minimumAmountForEligibility;
    private final int quantity;

    PromotionItem(Menu item, int minimumAmountForEligibility, int quantity) {
        validate(minimumAmountForEligibility, quantity);
        this.item = item;
        this.minimumAmountForEligibility = minimumAmountForEligibility;
        this.quantity = quantity;
    }

    private void validate(int minimumAmountForEligibility, int quantity) {
        validateMinimumAmount(minimumAmountForEligibility);
        validateQuantity(quantity);
    }

    private void validateMinimumAmount(int minimumAmountForEligibility) {
        if (minimumAmountForEligibility < ZERO) {
            String exceptionMessage = String.format(INVALID_AMOUNTS, ZERO);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < ZERO) {
            String exceptionMessage = String.format(INVALID__QUANTITY, ZERO);
            throw new IllegalArgumentException(exceptionMessage);
        }
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
        if (quantity == ZERO) {
            return ZERO;
        }

        return item.calculateDiscountAmountsForPromotion(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public Menu getItem() {
        return item;
    }
}
