package christmas.model;

public class TotalDiscountAmounts {
    private final int discountAmounts;

    private TotalDiscountAmounts(int discountAmounts) {
        this.discountAmounts = discountAmounts;
    }

    public static TotalDiscountAmounts from(int amounts) {
        return new TotalDiscountAmounts(amounts);
    }

    public int calculateAmountsAfterDiscount(int orderAmounts) {
        return orderAmounts - this.discountAmounts;
    }

    public boolean isEligibleFor(int minimumAmountForEligibility) {
        return this.discountAmounts >= minimumAmountForEligibility;
    }

    public int getDiscountAmounts() {
        return discountAmounts;
    }
}
