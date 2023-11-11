package christmas.model;

import java.util.Objects;

public final class TotalDiscountAmounts {
    private static final String INVALID_DISCOUNT = "총 할인 금액은 %d원 이상이어야 합니다.";
    private static final int NO_TOTAL_DISCOUNT_AMOUNT = 0;
    private final int amounts;

    private TotalDiscountAmounts(int amounts) {
        validateAmount(amounts);
        this.amounts = amounts;
    }

    private void validateAmount(int amounts) {
        if (amounts < NO_TOTAL_DISCOUNT_AMOUNT) {
            String exceptionMessage = String.format(INVALID_DISCOUNT, NO_TOTAL_DISCOUNT_AMOUNT);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static TotalDiscountAmounts from(int amounts) {
        return new TotalDiscountAmounts(amounts);
    }

    public int calculateOrderAmountsAfterDiscount(int orderAmounts) {
        return orderAmounts - this.amounts;
    }

    public boolean isEligibleFor(int minimumAmountForEligibility) {
        return this.amounts >= minimumAmountForEligibility;
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
        TotalDiscountAmounts that = (TotalDiscountAmounts) o;
        return amounts == that.amounts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amounts);
    }
}
