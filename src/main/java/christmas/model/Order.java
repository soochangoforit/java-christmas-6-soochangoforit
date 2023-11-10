package christmas.model;

public class Order {

    private final Menu menu;
    private final Quantity quantity;

    private Order(Menu menu, Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public static Order from(String menuName, int quantity) {
        Menu menu = Menu.from(menuName);
        Quantity menuQuantity = Quantity.from(quantity);

        return new Order(menu, menuQuantity);
    }

    public int calculatePrice() {
        return menu.calculatePrice(quantity);
    }

    public boolean belongsTo(Category category) {
        return menu.belongsTo(category);
    }

    public Menu getMenu() {
        return menu;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
