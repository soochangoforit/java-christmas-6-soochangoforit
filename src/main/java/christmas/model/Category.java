package christmas.model;

public enum Category {
    APPETIZER,
    MAIN,
    DESSERT,
    BEVERAGE;

    public boolean isBeverage() {
        return this == BEVERAGE;
    }
}
