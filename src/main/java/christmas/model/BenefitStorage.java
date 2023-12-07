package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public class BenefitStorage {
    private final Map<BenefitType, BenefitPolicy> discountPolicies;

    public BenefitStorage(Map<BenefitType, BenefitPolicy> discountPolicies) {
        this.discountPolicies = discountPolicies;
    }

    public BenefitResult applyBenefits(OrderInfo orderInfo) {
        if (orderInfo.orderAmountsIsGreaterThanOrEqualTo(10_000)) {
            Map<BenefitType, BenefitAmounts> result = new EnumMap<>(BenefitType.class);
            discountPolicies.forEach((type, policy) -> result.put(type, policy.applyBenefit(orderInfo)));
            return BenefitResult.from(result);
        }
        return BenefitResult.empty();
    }
}
