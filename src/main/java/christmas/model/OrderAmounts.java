package christmas.model;

public class OrderAmounts {
    private final int amounts;

    private OrderAmounts(int amounts) {
        this.amounts = amounts;
    }

    public boolean isEligibleFor(int minimumAmountForEligibility) {
        return amounts >= minimumAmountForEligibility;
    }

    public OrderAmounts subtract(TotalDiscountAmounts totalDiscountedAmounts) {
        int amountsAfterDiscount = totalDiscountedAmounts.calculateAmountsAfterDiscount(amounts);

        return OrderAmounts.from(amountsAfterDiscount);
    }

    public static OrderAmounts from(int amounts) {
        return new OrderAmounts(amounts);
    }

    public int getAmounts() {
        return amounts;
    }
}
