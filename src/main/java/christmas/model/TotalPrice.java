package christmas.model;

import java.util.Objects;

public final class TotalPrice {
    private final int price;

    private TotalPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (isNonPositive(price)) {
            throw new IllegalArgumentException("주문에 대한 총 가격은 0원을 넘어서야 합니다.");
        }
    }

    private boolean isNonPositive(int price) {
        return price <= 0;
    }

    public static TotalPrice from(int price) {
        return new TotalPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TotalPrice that = (TotalPrice) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
