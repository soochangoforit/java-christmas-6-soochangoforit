package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public class DiscountPolicyManager {
    private static final int MINIMUM_PRICE_FOR_DISCOUNT = 10_000;
    private final Map<DiscountType, DiscountPolicy> policyRegistry;

    private DiscountPolicyManager(Map<DiscountType, DiscountPolicy> policyRegistry) {
        this.policyRegistry = policyRegistry;
    }

    public static DiscountPolicyManager from(Map<DiscountType, DiscountPolicy> discountPolicies) {
        return new DiscountPolicyManager(discountPolicies);
    }

    public AppliedDiscounts applyDiscountPolicies(OrderResult orderResult) {
        Map<DiscountType, DiscountedAmount> discountResults = new EnumMap<>(DiscountType.class);
        if (isEligibleForDiscount(orderResult)) {
            policyRegistry.forEach((discountType, discountPolicy) -> {
                DiscountedAmount discountedAmount = discountPolicy.applyDiscount(orderResult);
                discountResults.put(discountType, discountedAmount);
            });
        }

        return AppliedDiscounts.from(discountResults);
    }

    private boolean isEligibleForDiscount(OrderResult orderResult) {
        int totalPrice = orderResult.calculateTotalPrice();

        return totalPrice >= MINIMUM_PRICE_FOR_DISCOUNT;
    }
}
