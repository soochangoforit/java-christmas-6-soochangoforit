package christmas.dto.response;

import java.util.List;
import christmas.model.Order;
import christmas.model.OrderGroup;
import christmas.model.OrderResult;

public class OrderResultDto {
    private final List<OrderInfoDto> orderInfoDtos;

    private OrderResultDto(List<OrderInfoDto> orderInfoDtos) {
        this.orderInfoDtos = orderInfoDtos;
    }

    public static OrderResultDto from(OrderResult orderResult) {
        OrderGroup orderGroup = orderResult.getOrderGroup();
        List<Order> orders = orderGroup.getOrders();

        List<OrderInfoDto> orderInfoDtos = orders.stream()
                .map(OrderInfoDto::from)
                .toList();

        return new OrderResultDto(orderInfoDtos);
    }

    public List<OrderInfoDto> getMenuInfoDtos() {
        return orderInfoDtos;
    }
}
