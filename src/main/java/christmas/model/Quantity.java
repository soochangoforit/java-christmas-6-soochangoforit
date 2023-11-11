package christmas.model;

import java.util.Objects;

public final class Quantity {
    private static final String COUNT_IS_BELOW_MIN_COUNT = "유효하지 않은 주문입니다";
    private static final int MIN_COUNT = 1;
    private final int count;

    private Quantity(int count) {
        validate(count);
        this.count = count;
    }

    private void validate(int count) {
        if (isBelowMinCount(count)) {
            throw new IllegalArgumentException("주문 하시는 메뉴 수량은 1 이상이어야 합니다.\n" + COUNT_IS_BELOW_MIN_COUNT);
        }
    }

    private boolean isBelowMinCount(int count) {
        return count < MIN_COUNT;
    }

    public static Quantity from(int count) {
        return new Quantity(count);
    }

    public int calculateAmounts(int menuPrice) {
        return count * menuPrice;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return count == quantity.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
