package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public final class DiscountEventManager {
    private static final int MINIMUM_AMOUNT_FOR_DISCOUNT = 10_000;

    private final Map<DiscountEventType, DiscountPolicy> discountEventRegistry;

    private DiscountEventManager(Map<DiscountEventType, DiscountPolicy> discountEventRegistry) {
        this.discountEventRegistry = discountEventRegistry;
    }

    public static DiscountEventManager from(Map<DiscountEventType, DiscountPolicy> discountEventRegistry) {
        return new DiscountEventManager(discountEventRegistry);
    }

    public AppliedDiscountEventResult applyDiscountEvents(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountAmounts> appliedDiscountEventResult = generateDiscountEventResult(orderInfo);

        return AppliedDiscountEventResult.from(appliedDiscountEventResult);
    }

    private Map<DiscountEventType, DiscountAmounts> generateDiscountEventResult(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountAmounts> appliedDiscountEventResult = new EnumMap<>(DiscountEventType.class);
        if (isEligibleForDiscount(orderInfo)) {
            applyDiscounts(orderInfo, appliedDiscountEventResult);
        }

        return appliedDiscountEventResult;
    }

    private boolean isEligibleForDiscount(OrderInfo orderInfo) {
        OrderAmounts orderAmounts = orderInfo.calculateOrderAmounts();

        return orderAmounts.isEligibleFor(MINIMUM_AMOUNT_FOR_DISCOUNT);
    }

    private void applyDiscounts(OrderInfo orderInfo, Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        discountEventRegistry.forEach((discountEventType, discountPolicy) -> {
            DiscountAmounts discountAmounts = applyDiscount(orderInfo, discountPolicy);
            discountEventResult.put(discountEventType, discountAmounts);
        });
    }

    private DiscountAmounts applyDiscount(OrderInfo orderInfo, DiscountPolicy discountPolicy) {
        return discountPolicy.applyDiscount(orderInfo);
    }
}
