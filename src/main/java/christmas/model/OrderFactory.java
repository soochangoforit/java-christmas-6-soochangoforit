package christmas.model;

import java.util.List;
import christmas.dto.request.OrderItemDto;

public class OrderFactory {

    public static Order create(List<OrderItemDto> orderItemDtos) {
        List<OrderItem> orderItems = orderItemDtos.stream()
                .map(dto -> OrderItem.from(Menu.from(dto.getMenuName()), Quantity.from(dto.getQuantity())))
                .toList();

        return Order.from(orderItems);
    }
}
