package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class OrderInfo {
    private final Order order;
    private final VisitDate visitDate;

    private OrderInfo(Order order, VisitDate visitDate) {
        this.order = order;
        this.visitDate = visitDate;
    }

    public static OrderInfo of(Order order, VisitDate visitDate) {
        return new OrderInfo(order, visitDate);
    }

    public int calculateTotalPrice() {
        return order.calculateTotalPrice();
    }

    public boolean isOrderedBetween(LocalDate startDate, LocalDate endDate) {
        return visitDate.isBetween(startDate, endDate);
    }

    public int daysSinceStartDate(LocalDate startDate) {
        return visitDate.daysSince(startDate);
    }

    public boolean isOrderedIn(EnumSet<DayOfWeek> dayOfWeek) {
        return visitDate.matchesDayOfWeek(dayOfWeek);
    }

    public int totalMenuQuantityOfCategory(Category category) {
        return order.totalMenuQuantityOfCategory(category);
    }

    public boolean isOrderedIn(Set<Integer> days) {
        return visitDate.matchesDays(days);
    }

    public boolean isQualifiedForPromotion(PromotionItem promotionItem) {
        return order.isQualifiedForPromotion(promotionItem);
    }

    public Order getOrderGroup() {
        return order;
    }

    public VisitDate getDateOfVisit() {
        return visitDate;
    }
}
