package christmas.model;

import java.time.LocalDate;

public class ChristmasEvent implements BenefitPolicy {
    private static final LocalDate EVENT_START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate EVENT_END_DATE = LocalDate.of(2023, 12, 25);

    @Override
    public BenefitAmounts applyBenefit(OrderInfo orderInfo) {
        if (orderInfo.isOrderedBetween(EVENT_START_DATE, EVENT_END_DATE)) {
            return calculateBenefitAmounts(orderInfo);
        }

        return BenefitAmounts.noBenefit();
    }

    private BenefitAmounts calculateBenefitAmounts(OrderInfo orderInfo) {
        int days = orderInfo.daysFrom(EVENT_START_DATE);
        int discountAmount = 1000 + (days * 100);

        return new BenefitAmounts(discountAmount);
    }
}
