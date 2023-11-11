package christmas.model;

public class Order {

    private final Menu menu;
    private final Quantity quantity;

    private Order(String menu, int quantity) {
        this(Menu.from(menu), Quantity.from(quantity));
    }

    private Order(Menu menu, Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public static Order from(OrderInfo orderInfo) {
        String menuName = orderInfo.getMenuName();
        int quantity = orderInfo.getQuantity();

        return new Order(menuName, quantity);
    }

    public int calculatePrice() {
        return menu.calculatePrice(quantity);
    }

    public boolean belongsTo(Category category) {
        return menu.belongsTo(category);
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
