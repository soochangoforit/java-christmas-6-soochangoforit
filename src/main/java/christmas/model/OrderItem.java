package christmas.model;

public class OrderItem {

    private final Menu menu;
    private final Quantity quantity;

    private OrderItem(String menu, int quantity) {
        this(Menu.from(menu), Quantity.from(quantity));
    }

    private OrderItem(Menu menu, Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public static OrderItem from(OrderItemMapper orderItemMapper) {
        String menuName = orderItemMapper.getMenuName();
        int quantity = orderItemMapper.getQuantity();

        return new OrderItem(menuName, quantity);
    }

    public int calculateAmounts() {
        return menu.calculateAmounts(quantity);
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
