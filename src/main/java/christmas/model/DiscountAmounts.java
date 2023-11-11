package christmas.model;

import java.util.Objects;

public final class DiscountAmounts {
    private static final String INVALID_DISCOUNT = "할인 금액은 %d원 이상이어야 합니다.";
    private static final int NO_DISCOUNT_AMOUNT = 0;

    private final int amounts;

    private DiscountAmounts(int amounts) {
        validateAmount(amounts);
        this.amounts = amounts;
    }

    private void validateAmount(int amounts) {
        if (amounts < NO_DISCOUNT_AMOUNT) {
            String exceptionMessage = String.format(INVALID_DISCOUNT, NO_DISCOUNT_AMOUNT);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static DiscountAmounts noDiscount() {
        return new DiscountAmounts(NO_DISCOUNT_AMOUNT);
    }

    public static DiscountAmounts from(int amounts) {
        return new DiscountAmounts(amounts);
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
        DiscountAmounts that = (DiscountAmounts) o;
        return amounts == that.amounts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amounts);
    }
}
