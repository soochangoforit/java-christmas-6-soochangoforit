package christmas.model;

import java.util.Objects;

public final class DiscountAmounts {
    private static final String DISCOUNT_AMOUNT_IS_NEGATIVE = "할인 금액은 음수가 될 수 없습니다";
    private static final int ZERO = 0;
    private final int amounts;

    private DiscountAmounts(int amounts) {
        validatePositive(amounts);
        this.amounts = amounts;
    }

    private void validatePositive(int amounts) {
        if (amounts < ZERO) {
            throw new IllegalArgumentException(DISCOUNT_AMOUNT_IS_NEGATIVE);
        }
    }

    public static DiscountAmounts zero() {
        return new DiscountAmounts(ZERO);
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
