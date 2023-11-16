package christmas.model;

import christmas.dto.request.OrderItemInfoDto;

public final class OrderItemMapper {
    private final String menuName;
    private final int quantity;

    OrderItemMapper(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static OrderItemMapper from(OrderItemInfoDto orderItemInfoDto) {
        String menuName = orderItemInfoDto.getMenuName();
        int quantity = orderItemInfoDto.getQuantity();

        return new OrderItemMapper(menuName, quantity);
    }

    public String getMenuName() {
        return menuName;
    }

    public int getQuantity() {
        return quantity;
    }
}
