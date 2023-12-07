package christmas.model;

import java.time.LocalDate;

public class PromotionEvent implements BenefitPolicy {
    private static final LocalDate EVENT_START_DATE = LocalDate.of(2023, 12, 1);
    private static final LocalDate EVENT_END_DATE = LocalDate.of(2023, 12, 31);
    private static final PromotionItem promotionItem = PromotionItem.FREE_CHAMPAGNE;

    @Override
    public BenefitAmounts applyBenefit(OrderInfo orderInfo) {
        if (orderInfo.isOrderedBetween(EVENT_START_DATE, EVENT_END_DATE) && orderInfo.isEligibleFor(promotionItem)) {
            int benefitAmounts = promotionItem.calculateBenefitAmounts();

            return BenefitAmounts.from(benefitAmounts);
        }
        return BenefitAmounts.noBenefit();
    }
}
