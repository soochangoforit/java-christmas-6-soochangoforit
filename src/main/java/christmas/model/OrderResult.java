package christmas.model;

public class OrderResult {
    private final OrderGroup orderGroup;
    private final DateOfVisit dateOfVisit;

    private OrderResult(OrderGroup orderGroup, DateOfVisit dateOfVisit) {
        this.orderGroup = orderGroup;
        this.dateOfVisit = dateOfVisit;
    }

    public static OrderResult of(OrderGroup orderGroup, DateOfVisit dateOfVisit) {
        return new OrderResult(orderGroup, dateOfVisit);
    }

    public int calculateTotalPrice() {
        return orderGroup.calculateTotalPrice();
    }

    public OrderGroup getOrderGroup() {
        return orderGroup;
    }

    public DateOfVisit getDateOfVisit() {
        return dateOfVisit;
    }
}
