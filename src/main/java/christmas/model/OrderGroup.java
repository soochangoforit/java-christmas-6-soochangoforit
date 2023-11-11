package christmas.model;

import java.util.List;

public class OrderGroup {
    private static final String ORDERS_ARE_EMPTY = "주문 목록은 비어있을 수 없습니다.";
    private static final String DUPLICATED_MENU = "유효하지 않은 주문입니다.";
    private static final String TOTAL_MENU_QUANTITY_IS_ABOVE_MAX_COUNT = "총 주문 가능한 메뉴 수량은 20개 이하입니다.";
    private static final String ONLY_BEVERAGE = "음료만 주문할 수 없습니다.";
    private final List<Order> orders;

    private OrderGroup(List<Order> orders) {
        validate(orders);
        this.orders = orders;
    }

    private void validate(List<Order> orders) {
        validateEmpty(orders);
        validateDuplicateMenu(orders);
        validateTotalQuantity(orders);
        validateOnlyBeverage(orders);
    }

    private static void validateEmpty(List<Order> orders) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException(ORDERS_ARE_EMPTY);
        }
    }

    private void validateDuplicateMenu(List<Order> orders) {
        if (hasDuplicateMenu(orders)) {
            throw new IllegalArgumentException("중복된 메뉴 이름이 있습니다.\n" + DUPLICATED_MENU);
        }
    }

    private boolean hasDuplicateMenu(List<Order> orders) {
        return countDistinctMenu(orders) != orders.size();
    }

    private int countDistinctMenu(List<Order> orders) {
        return (int) orders.stream()
                .map(Order::getMenu)
                .distinct()
                .count();
    }

    private void validateOnlyBeverage(List<Order> orders) {
        if (hasOnlyBeverage(orders)) {
            throw new IllegalArgumentException(ONLY_BEVERAGE);
        }
    }

    private boolean hasOnlyBeverage(List<Order> orders) {
        return orders.stream()
                .allMatch(Order::isBeverage);
    }

    private void validateTotalQuantity(List<Order> orders) {
        if (hasInvalidQuantity(orders)) {
            throw new IllegalArgumentException(TOTAL_MENU_QUANTITY_IS_ABOVE_MAX_COUNT);
        }
    }

    private boolean hasInvalidQuantity(List<Order> orders) {
        return orders.stream()
                .map(Order::getQuantity)
                .mapToInt(Quantity::getCount)
                .sum() > 20;
    }

    public static OrderGroup from(List<OrderInfo> orderInfos) {
        List<Order> orders = orderInfos.stream()
                .map(Order::from)
                .toList();

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
