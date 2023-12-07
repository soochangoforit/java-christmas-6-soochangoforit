package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class WeekdayEvent implements BenefitPolicy {
    private static final LocalDate EVENT_START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate EVENT_END_DATE = LocalDate.of(2023, 12, 31);
    private final List<DayOfWeek> weekDays = List.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);

    @Override
    public BenefitAmounts applyBenefit(OrderInfo orderInfo) {
        if (orderInfo.isOrderedBetween(EVENT_START_DATE, EVENT_END_DATE) && orderInfo.isOrderedIn(weekDays)) {
            return calculateBenefitAmounts(orderInfo);
        }

        return BenefitAmounts.noBenefit();
    }

    private BenefitAmounts calculateBenefitAmounts(OrderInfo orderInfo) {
        int countOfItems = orderInfo.calculateItemCountOf(Category.DESSERT);

        return new BenefitAmounts(countOfItems * 2_023);
    }
}
