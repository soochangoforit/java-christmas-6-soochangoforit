package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

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

    public boolean isOrderedBetween(LocalDate startDate, LocalDate endDate) {
        return dateOfVisit.isBetween(startDate, endDate);
    }

    public int calculateDaysFrom(LocalDate startDate) {
        return dateOfVisit.calculateDaysFrom(startDate);
    }

    public boolean isOrderedIn(EnumSet<DayOfWeek> dayOfWeek) {
        return dateOfVisit.isOrderedIn(dayOfWeek);
    }

    public int countOrdersIn(Category category) {
        return orderGroup.countOrdersIn(category);
    }

    public boolean isOrderedIn(Set<Integer> days) {
        return dateOfVisit.isOrderedIn(days);
    }

    public boolean isQualifiedForPromotion(PromotionItem promotionItem) {
        return orderGroup.isQualifiedForPromotion(promotionItem);
    }

    public OrderGroup getOrderGroup() {
        return orderGroup;
    }

    public DateOfVisit getDateOfVisit() {
        return dateOfVisit;
    }
}
