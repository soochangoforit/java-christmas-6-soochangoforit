package christmas.model;

import java.util.Objects;

public final class OrderAmounts {
    private static final String AMOUNTS_IS_BELOW_MIN_AMOUNTS = "주문 총액은 %d원 이상이어야 합니다.";
    private static final int MINIMUM_AMOUNTS = 0;

    private final int amounts;

    private OrderAmounts(int amounts) {
        validateMinimumAmounts(amounts);
        this.amounts = amounts;
    }

    private void validateMinimumAmounts(int amounts) {
        if (amounts < MINIMUM_AMOUNTS) {
            String exceptionMessage = String.format(AMOUNTS_IS_BELOW_MIN_AMOUNTS, MINIMUM_AMOUNTS);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public boolean isEligibleFor(int minimumAmountForEligibility) {
        return amounts >= minimumAmountForEligibility;
    }

    public OrderAmounts deductDiscount(TotalDiscountAmounts totalDiscountedAmounts) {
        int orderAmountsAfterDiscount = totalDiscountedAmounts.calculateOrderAmountsAfterDiscount(amounts);

        return OrderAmounts.from(orderAmountsAfterDiscount);
    }

    public static OrderAmounts from(int amounts) {
        return new OrderAmounts(amounts);
    }

    public int getAmounts() {
        return amounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderAmounts that = (OrderAmounts) o;
        return amounts == that.amounts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amounts);
    }
}
