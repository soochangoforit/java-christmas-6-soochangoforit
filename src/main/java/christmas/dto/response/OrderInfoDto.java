package christmas.dto.response;

import christmas.model.Menu;
import christmas.model.Order;
import christmas.model.Quantity;

public class OrderInfoDto {
    private final String menuName;
    private final int quantity;

    private OrderInfoDto(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static OrderInfoDto from(Order order) {
        Menu menu = order.getMenu();
        String menuName = menu.getMenuName();
        Quantity quantity = order.getQuantity();
        int menuQuantity = quantity.getCount();

        return new OrderInfoDto(menuName, menuQuantity);
    }

    public String getMenuName() {
        return menuName;
    }

    public int getQuantity() {
        return quantity;
    }
}
