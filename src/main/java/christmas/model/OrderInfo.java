package christmas.model;

public class OrderInfo {
    private final VisitDate visitDate;
    private final Order order;

    public OrderInfo(VisitDate visitDate, Order order) {
        this.visitDate = visitDate;
        this.order = order;
    }

    public static OrderInfo from(VisitDate visitDate, Order order) {
        return new OrderInfo(visitDate, order);
    }

    public int calculateOrderAmounts() {
        return order.calculateTotalAmount();
    }

    public VisitDate getVisitDate() {
        return visitDate;
    }

    public Order getOrder() {
        return order;
    }
}
