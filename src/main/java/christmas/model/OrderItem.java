package christmas.model;

public class OrderItem {
    private final Menu menu;
    private final Quantity quantity;

    public OrderItem(Menu menu, Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public static OrderItem from(Menu menu, Quantity quantity) {
        return new OrderItem(menu, quantity);
    }

    public boolean isBeverage() {
        return menu.isBeverage();
    }

    public Menu getMenu() {
        return menu;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
