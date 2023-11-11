package christmas.model;

import christmas.dto.request.OrderInfoDto;

public class OrderInfo {
    private final String menuName;
    private final int quantity;

    private OrderInfo(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static OrderInfo of(OrderInfoDto orderInfoDto) {
        String menuName = orderInfoDto.getMenuName();
        int quantity = orderInfoDto.getQuantity();

        return new OrderInfo(menuName, quantity);
    }

    public String getMenuName() {
        return menuName;
    }

    public int getQuantity() {
        return quantity;
    }
}
