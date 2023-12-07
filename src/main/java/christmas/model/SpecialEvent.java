package christmas.model;

import java.time.LocalDate;
import java.util.Set;

public class SpecialEvent implements BenefitPolicy {
    private static final LocalDate EVENT_START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate EVENT_END_DATE = LocalDate.of(2023, 12, 31);

    private final Set<Integer> eventDays = Set.of(3, 10, 17, 24, 25, 31);

    @Override
    public BenefitAmounts applyBenefit(OrderInfo orderInfo) {
        if (orderInfo.isOrderedBetween(EVENT_START_DATE, EVENT_END_DATE) && orderInfo.isOrderedIn(eventDays)) {
            return calculateBenefitAmounts();
        }

        return BenefitAmounts.noBenefit();
    }

    private BenefitAmounts calculateBenefitAmounts() {
        return BenefitAmounts.from(1_000);
    }
}
