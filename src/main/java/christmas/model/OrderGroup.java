package christmas.model;

import java.util.List;

public class OrderGroup {
    private final List<Order> orders;

    private OrderGroup(List<Order> orders) {
        validate(orders);
        this.orders = orders;
    }

    private void validate(List<Order> orders) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("주문은 최소 1개 이상이어야 합니다.");
        }
        if (hasDuplicateMenu(orders)) {
            throw new IllegalArgumentException("중복된 메뉴 이름이 있습니다.");
        }
        if (hasInvalidQuantity(orders)) {
            throw new IllegalArgumentException("총 주문 가능한 수량은 20개 이하입니다.");
        }
        if (hasOnlyBeverage(orders)) {
            throw new IllegalArgumentException("음료만 주문할 수 없습니다.");
        }
    }

    private boolean hasDuplicateMenu(List<Order> orders) {
        return orders.stream()
                .map(Order::getMenu)
                .distinct()
                .count() != orders.size();
    }

    private boolean hasInvalidQuantity(List<Order> orders) {
        return orders.stream()
                .map(Order::getQuantity)
                .mapToInt(Quantity::getCount)
                .sum() > 20;
    }

    private boolean hasOnlyBeverage(List<Order> orders) {
        return orders.stream()
                .map(Order::getMenu)
                .allMatch(Menu::isBeverage);
    }

    public static OrderGroup create(List<OrderInfo> orderInfos) {
        List<Order> orders = orderInfos.stream()
                .map(Order::from)
                .toList();

        return new OrderGroup(orders);
    }

    public static OrderGroup from(List<Order> orders) {
        return new OrderGroup(orders);
    }

    public boolean isQualifiedForPromotion(PromotionItem promotionItem) {
        int totalPrice = calculateTotalPrice();
        return promotionItem.isEligible(totalPrice);
    }

    public int calculateTotalPrice() {
        return orders.stream()
                .mapToInt(Order::calculatePrice)
                .sum();
    }

    public int totalMenuQuantityOfCategory(Category category) {
        int totalMenuQuantity = orders.stream()
                .filter(order -> order.belongsTo(category))
                .map(Order::getQuantity)
                .mapToInt(Quantity::getCount)
                .sum();

        return totalMenuQuantity;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
