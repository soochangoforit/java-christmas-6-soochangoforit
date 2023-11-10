package christmas.model;

import java.util.List;
import christmas.dto.request.OrderInfoDto;

public class OrderService {

    public OrderGroup createOrderGroup(List<OrderInfoDto> orderInfos) {
        List<Order> orders = orderInfos.stream()
                .map(this::createOrder)
                .toList();

        return OrderGroup.from(orders);
    }

    private Order createOrder(OrderInfoDto orderInfoDto) {
        String menuName = orderInfoDto.getMenuName();
        int quantity = orderInfoDto.getQuantity();

        return Order.from(menuName, quantity);
    }
}
