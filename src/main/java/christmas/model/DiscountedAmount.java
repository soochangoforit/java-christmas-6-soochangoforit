package christmas.model;

import java.util.Objects;

public final class DiscountedAmount {
    private final int amount;

    private DiscountedAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("할인 금액은 0원 이상이어야 합니다.");
        }
    }

    public static DiscountedAmount zero() {
        return new DiscountedAmount(0);
    }

    public static DiscountedAmount from(int amount) {
        return new DiscountedAmount(amount);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountedAmount that = (DiscountedAmount) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
