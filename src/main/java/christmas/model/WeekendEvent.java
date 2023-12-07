package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class WeekendEvent implements BenefitPolicy {
    private static final LocalDate EVENT_START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate EVENT_END_DATE = LocalDate.of(2023, 12, 31);

    private final List<DayOfWeek> weekEnds = List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

    @Override
    public BenefitAmounts applyBenefit(OrderInfo orderInfo) {
        if (orderInfo.isOrderedBetween(EVENT_START_DATE, EVENT_END_DATE) && orderInfo.isOrderedIn(weekEnds)) {
            return calculateBenefitAmounts(orderInfo);
        }
        return BenefitAmounts.noBenefit();
    }

    private BenefitAmounts calculateBenefitAmounts(OrderInfo orderInfo) {
        int countOfItems = orderInfo.calculateItemCountOf(Category.MAIN);

        return new BenefitAmounts(countOfItems * 2_023);
    }
}
