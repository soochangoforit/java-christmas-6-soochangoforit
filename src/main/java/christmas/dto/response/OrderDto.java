package christmas.dto.response;

import java.util.List;
import christmas.model.Order;
import christmas.model.OrderInfo;
import christmas.model.OrderItem;

public class OrderDto {
    private final List<OrderItemDto> orderItemDtos;

    private OrderDto(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }

    public static OrderDto from(OrderInfo orderInfo) {
        Order order = orderInfo.getOrder();
        List<OrderItem> orderItems = order.getOrderItems();

        List<OrderItemDto> orderItemDtos = convertFrom(orderItems);

        return new OrderDto(orderItemDtos);
    }

    private static List<OrderItemDto> convertFrom(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemDto::from)
                .toList();
    }

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }
}
