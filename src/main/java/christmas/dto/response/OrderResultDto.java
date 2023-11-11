package christmas.dto.response;

import java.util.List;
import christmas.model.Order;
import christmas.model.OrderInfo;
import christmas.model.OrderItem;

public class OrderResultDto {
    private final List<OrderInfoDto> orderInfoDtos;

    private OrderResultDto(List<OrderInfoDto> orderInfoDtos) {
        this.orderInfoDtos = orderInfoDtos;
    }

    public static OrderResultDto from(OrderInfo orderInfo) {
        Order order = orderInfo.getOrderGroup();
        List<OrderItem> orderItems = order.getOrders();

        List<OrderInfoDto> orderInfoDtos = orderItems.stream()
                .map(OrderInfoDto::from)
                .toList();

        return new OrderResultDto(orderInfoDtos);
    }

    public List<OrderInfoDto> getMenuInfoDtos() {
        return orderInfoDtos;
    }
}
