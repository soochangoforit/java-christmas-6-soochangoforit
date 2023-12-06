package christmas.model;

public enum Category {
    APPETIZER,
    MAIN,
    DESSERT,
    BEVERAGE, NONE;

    public boolean isBeverage() {
        return this == BEVERAGE;
    }
}
