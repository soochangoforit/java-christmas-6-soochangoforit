package christmas.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> orderItems;

    public Order(List<OrderItem> orderItems) {
        validate(orderItems);
        this.orderItems = new ArrayList<>(orderItems);
    }

    private void validate(List<OrderItem> orderItems) {
        validateEmpty(orderItems);
        validateNoDuplicateItems(orderItems);
        validateNotOnlyBeverages(orderItems);
        validateMaxItems(orderItems);
    }

    private void validateEmpty(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("주문 목록이 비어있을 순 없습니다.");
        }
    }

    private void validateNoDuplicateItems(List<OrderItem> orderItems) {
        List<Menu> menus = orderItems.stream()
                .map(OrderItem::getMenu)
                .toList();

        if (menus.size() != menus.stream().distinct().count()) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다.");
        }
    }

    private void validateNotOnlyBeverages(List<OrderItem> orderItems) {
        boolean onlyBeverages = orderItems.stream()
                .allMatch(OrderItem::isBeverage);

        if (onlyBeverages) {
            throw new IllegalArgumentException("음료만 주문할 수 없습니다.");
        }
    }

    private void validateMaxItems(List<OrderItem> orderItems) {
        int totalItems = orderItems.stream()
                .mapToInt(orderItem -> orderItem.getQuantity().getValue())
                .sum();

        if (totalItems > 20) {
            throw new IllegalArgumentException("주문은 한 번에 최대 20개 메뉴지까만 주문할 수 있습니다");
        }
    }

    public static Order from(List<OrderItem> orderItems) {
        return new Order(orderItems);
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }
}
