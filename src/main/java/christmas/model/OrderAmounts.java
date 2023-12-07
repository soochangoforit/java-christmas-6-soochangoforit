package christmas.model;

public class OrderAmounts {
    private final int amounts;

    public OrderAmounts(int amounts) {
        this.amounts = amounts;
    }

    public static OrderAmounts from(int amounts) {
        return new OrderAmounts(amounts);
    }

    public OrderAmounts minus(int totalDiscountAmounts) {
        return new OrderAmounts(amounts - totalDiscountAmounts);
    }

    public boolean isGreaterThanOrEqualTo(int minimumOrderAmounts) {
        return amounts >= minimumOrderAmounts;
    }

    public int getAmounts() {
        return amounts;
    }
}
