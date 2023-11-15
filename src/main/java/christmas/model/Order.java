package christmas.model;

import java.util.ArrayList;
import java.util.List;

public final class Order {
    private static final String ORDERS_ARE_EMPTY = "[ERROR] 주문 목록은 비어있을 수 없습니다.";
    private static final String DUPLICATED_MENU = "[ERROR] 유효하지 않은 주문입니다.";
    private static final String OVER_MAX_TOTAL_ORDER_ITEM_QUANTITY = "[ERROR] 총 주문 가능한 메뉴 수량은 %d개 이하입니다.";
    private static final String ONLY_BEVERAGE = "[ERROR] 음료만 주문할 수 없습니다.";
    private static final int MAX_TOTAL_ORDER_ITEM_QUANTITY = 20;

    private final List<OrderItem> orderItems;

    Order(List<OrderItem> orderItems) {
        validate(orderItems);
        this.orderItems = new ArrayList<>(orderItems);
    }

    private void validate(List<OrderItem> orderItems) {
        validateEmpty(orderItems);
        validateDuplicateMenu(orderItems);
        validateTotalQuantity(orderItems);
        validateHasOnlyBeverage(orderItems);
    }

    private void validateEmpty(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException(ORDERS_ARE_EMPTY);
        }
    }

    private void validateDuplicateMenu(List<OrderItem> orderItems) {
        if (hasDuplicateMenu(orderItems)) {
            throw new IllegalArgumentException(DUPLICATED_MENU);
        }
    }

    private boolean hasDuplicateMenu(List<OrderItem> orderItems) {
        return countDistinctMenu(orderItems) != orderItems.size();
    }

    private int countDistinctMenu(List<OrderItem> orderItems) {
        return (int) orderItems.stream()
                .map(OrderItem::getMenu)
                .distinct()
                .count();
    }

    private void validateTotalQuantity(List<OrderItem> orderItems) {
        if (hasInvalidTotalOrderItemQuantity(orderItems)) {
            String exceptionMessage = String.format(OVER_MAX_TOTAL_ORDER_ITEM_QUANTITY, MAX_TOTAL_ORDER_ITEM_QUANTITY);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private boolean hasInvalidTotalOrderItemQuantity(List<OrderItem> orderItems) {
        return sumTotalOrderItemQuantityIn(orderItems) > MAX_TOTAL_ORDER_ITEM_QUANTITY;
    }

    private int sumTotalOrderItemQuantityIn(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getQuantity)
                .mapToInt(Quantity::getCount)
                .sum();
    }

    private void validateHasOnlyBeverage(List<OrderItem> orderItems) {
        if (hasOnlyBeverage(orderItems)) {
            throw new IllegalArgumentException(ONLY_BEVERAGE);
        }
    }

    private boolean hasOnlyBeverage(List<OrderItem> orderItems) {
        return orderItems.stream()
                .allMatch(OrderItem::isBeverage);
    }

    public static Order from(List<OrderItemMapper> orderItemMappers) {
        List<OrderItem> orderItems = createOrderItems(orderItemMappers);

        return new Order(orderItems);
    }

    private static List<OrderItem> createOrderItems(List<OrderItemMapper> orderItemMappers) {
        return orderItemMappers.stream()
                .map(OrderItem::from)
                .toList();
    }

    public boolean isEligibleFor(PromotionItem promotionItem) {
        OrderAmounts orderAmounts = calculateOrderAmounts();

        return promotionItem.isEligibleFor(orderAmounts);
    }

    public OrderAmounts calculateOrderAmounts() {
        int orderAmounts = sumTotalOrderItemAmounts();

        return OrderAmounts.from(orderAmounts);
    }

    private int sumTotalOrderItemAmounts() {
        return orderItems.stream()
                .mapToInt(OrderItem::calculateAmounts)
                .sum();
    }

    public int sumTotalOrderItemQuantityIn(Category category) {
        return orderItems.stream()
                .filter(orderItem -> orderItem.belongsTo(category))
                .map(OrderItem::getQuantity)
                .mapToInt(Quantity::getCount)
                .sum();
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }
}
