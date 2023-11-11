package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class OrderResult {
    private final OrderGroup orderGroup;
    private final VisitDate visitDate;

    private OrderResult(OrderGroup orderGroup, VisitDate visitDate) {
        this.orderGroup = orderGroup;
        this.visitDate = visitDate;
    }

    public static OrderResult of(OrderGroup orderGroup, VisitDate visitDate) {
        return new OrderResult(orderGroup, visitDate);
    }

    public int calculateTotalPrice() {
        return orderGroup.calculateTotalPrice();
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
        return orderGroup.totalMenuQuantityOfCategory(category);
    }

    public boolean isOrderedIn(Set<Integer> days) {
        return visitDate.matchesDays(days);
    }

    public boolean isQualifiedForPromotion(PromotionItem promotionItem) {
        return orderGroup.isQualifiedForPromotion(promotionItem);
    }

    public OrderGroup getOrderGroup() {
        return orderGroup;
    }

    public VisitDate getDateOfVisit() {
        return visitDate;
    }
}
