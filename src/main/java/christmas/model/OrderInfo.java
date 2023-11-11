package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public final class OrderInfo {
    private final Order order;
    private final VisitDate visitDate;

    private OrderInfo(Order order, VisitDate visitDate) {
        this.order = order;
        this.visitDate = visitDate;
    }

    public static OrderInfo of(Order order, VisitDate visitDate) {
        return new OrderInfo(order, visitDate);
    }

    public OrderAmounts calculateOrderAmounts() {
        return order.calculateOrderAmounts();
    }

    public boolean isOrderedBetween(LocalDate startDate, LocalDate endDate) {
        return visitDate.isBetween(startDate, endDate);
    }

    public int daysSinceStartDate(LocalDate startDate) {
        return visitDate.daysSince(startDate);
    }

    public boolean isOrderedInDaysOfWeek(Set<DayOfWeek> dayOfWeek) {
        return visitDate.isInDayOfWeek(dayOfWeek);
    }

    public boolean isOrderedInDays(Set<Integer> days) {
        return visitDate.isInDays(days);
    }

    public int sumTotalOrderItemQuantityIn(Category category) {
        return order.sumTotalOrderItemQuantityIn(category);
    }

    public boolean isEligibleFor(PromotionItem promotionItem) {
        return order.isEligibleFor(promotionItem);
    }

    public Order getOrder() {
        return order;
    }
}
