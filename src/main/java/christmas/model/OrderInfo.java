package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    public boolean isOrderedBetween(LocalDate starDate, LocalDate endDate) {
        return visitDate.isBetween(starDate, endDate);
    }

    public int daysFrom(LocalDate eventStartDate) {
        return visitDate.daysFrom(eventStartDate);
    }

    public boolean isOrderedIn(List<DayOfWeek> weekDays) {
        return visitDate.isInDayOfWeeks(weekDays);
    }

    public int calculateItemCountOf(Category category) {
        return order.calculateItemCountOf(category);
    }

    public boolean isOrderedIn(Set<Integer> eventDays) {
        return visitDate.isInDays(eventDays);
    }

    public boolean orderAmountsIsOver(int minimumOrderAmounts) {
        return calculateOrderAmounts() >= minimumOrderAmounts;
    }

    public int calculateOrderAmounts() {
        return order.calculateTotalAmount();
    }

    public boolean isEligibleFor(PromotionItem promotionItem) {
        return order.isEligibleFor(promotionItem);
    }

    public VisitDate getVisitDate() {
        return visitDate;
    }

    public Order getOrder() {
        return order;
    }
}
