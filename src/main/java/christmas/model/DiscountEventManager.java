package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public final class DiscountEventManager {
    private static final int MINIMUM_PRICE_FOR_DISCOUNT = 10_000;
    private final Map<DiscountEventType, DiscountPolicy> discountEventRegistry;

    private DiscountEventManager(Map<DiscountEventType, DiscountPolicy> discountEventRegistry) {
        this.discountEventRegistry = discountEventRegistry;
    }

    public static DiscountEventManager from(Map<DiscountEventType, DiscountPolicy> discountEventRegistry) {
        return new DiscountEventManager(discountEventRegistry);
    }

    public AppliedDiscountEventResult applyDiscountEvents(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountAmounts> discountEventResult = generateDiscountEventResult(orderInfo);

        return AppliedDiscountEventResult.from(discountEventResult);
    }

    private Map<DiscountEventType, DiscountAmounts> generateDiscountEventResult(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountAmounts> discountEventResult = new EnumMap<>(DiscountEventType.class);
        if (isEligibleForDiscount(orderInfo)) {
            applyDiscounts(orderInfo, discountEventResult);
        }

        return discountEventResult;
    }

    private boolean isEligibleForDiscount(OrderInfo orderInfo) {
        OrderAmounts orderAmounts = orderInfo.calculateOrderAmounts();

        return orderAmounts.isEligibleFor(MINIMUM_PRICE_FOR_DISCOUNT);
    }

    private void applyDiscounts(OrderInfo orderInfo, Map<DiscountEventType, DiscountAmounts> discountEventResult) {
        discountEventRegistry.forEach((discountType, discountPolicy) -> {
            DiscountAmounts discountAmounts = applyDiscount(orderInfo, discountPolicy);
            discountEventResult.put(discountType, discountAmounts);
        });
    }

    private DiscountAmounts applyDiscount(OrderInfo orderInfo, DiscountPolicy discountPolicy) {
        return discountPolicy.applyDiscount(orderInfo);
    }

}
