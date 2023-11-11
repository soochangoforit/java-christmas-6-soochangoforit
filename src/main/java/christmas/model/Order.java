package christmas.model;

import java.util.List;

public class Order {
    private static final String ORDERS_ARE_EMPTY = "주문 목록은 비어있을 수 없습니다.";
    private static final String DUPLICATED_MENU = "유효하지 않은 주문입니다.";
    private static final String TOTAL_MENU_QUANTITY_IS_ABOVE_MAX_COUNT = "총 주문 가능한 메뉴 수량은 20개 이하입니다.";
    private static final String ONLY_BEVERAGE = "음료만 주문할 수 없습니다.";
    private static final int TOTAL_MENU_QUANTITY_MAX_COUNT = 20;
    private final List<OrderItem> orderItems;

    private Order(List<OrderItem> orderItems) {
        validate(orderItems);
        this.orderItems = orderItems;
    }

    private void validate(List<OrderItem> orderItems) {
        validateEmpty(orderItems);
        validateDuplicateMenu(orderItems);
        validateTotalQuantity(orderItems);
        validateHasOnlyBeverage(orderItems);
    }

    private static void validateEmpty(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException(ORDERS_ARE_EMPTY);
        }
    }

    private void validateDuplicateMenu(List<OrderItem> orderItems) {
        if (hasDuplicateMenu(orderItems)) {
            throw new IllegalArgumentException("중복된 메뉴 이름이 있습니다.\n" + DUPLICATED_MENU);
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
        if (hasInvalidQuantity(orderItems)) {
            throw new IllegalArgumentException(TOTAL_MENU_QUANTITY_IS_ABOVE_MAX_COUNT);
        }
    }

    private boolean hasInvalidQuantity(List<OrderItem> orderItems) {
        return calculateTotalQuantity(orderItems) > TOTAL_MENU_QUANTITY_MAX_COUNT;
    }

    private int calculateTotalQuantity(List<OrderItem> orderItems) {
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
        List<OrderItem> orderItems = orderItemMappers.stream()
                .map(OrderItem::from)
                .toList();

        return new Order(orderItems);
    }

    public boolean isQualifiedForPromotion(PromotionItem promotionItem) {
        int totalPrice = calculateTotalPrice();
        return promotionItem.isEligible(totalPrice);
    }

    public int calculateTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::calculatePrice)
                .sum();
    }

    public int totalMenuQuantityOfCategory(Category category) {
        int totalMenuQuantity = orderItems.stream()
                .filter(orderItem -> orderItem.belongsTo(category))
                .map(OrderItem::getQuantity)
                .mapToInt(Quantity::getCount)
                .sum();

        return totalMenuQuantity;
    }

    public List<OrderItem> getOrders() {
        return orderItems;
    }
}
