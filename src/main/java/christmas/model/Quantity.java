package christmas.model;

import java.util.Objects;

public final class Quantity {
    private final int count;

    private Quantity(int count) {
        validate(count);
        this.count = count;
    }

    private void validate(int count) {
        if (isNonPositive(count)) {
            throw new IllegalArgumentException("주문 하시는 메뉴 수량은 1 이상이어야 합니다.");
        }
    }

    private boolean isNonPositive(int count) {
        return count <= 0;
    }

    public static Quantity from(int count) {
        return new Quantity(count);
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
