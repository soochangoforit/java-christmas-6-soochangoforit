package christmas.model;

public class Quantity {
    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다.");
        }
    }

    public static Quantity from(int quantity) {
        return new Quantity(quantity);
    }

    public int getValue() {
        return value;
    }
}
