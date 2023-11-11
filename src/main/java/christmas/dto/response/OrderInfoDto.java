package christmas.dto.response;

import christmas.model.Menu;
import christmas.model.OrderItem;
import christmas.model.Quantity;

public class OrderInfoDto {
    private final String menuName;
    private final int quantity;

    private OrderInfoDto(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static OrderInfoDto from(OrderItem orderItem) {
        Menu menu = orderItem.getMenu();
        String menuName = menu.getName();
        Quantity quantity = orderItem.getQuantity();
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
