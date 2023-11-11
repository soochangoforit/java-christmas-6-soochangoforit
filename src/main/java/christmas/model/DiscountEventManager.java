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
        Map<DiscountEventType, DiscountedAmount> discountEventResult = generateDiscountEventResult(orderInfo);

        return AppliedDiscountEventResult.from(discountEventResult);
    }

    private Map<DiscountEventType, DiscountedAmount> generateDiscountEventResult(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountedAmount> discountEventResult = new EnumMap<>(DiscountEventType.class);
        if (isEligibleForDiscount(orderInfo)) {
            applyDiscounts(orderInfo, discountEventResult);
        }

        return discountEventResult;
    }

    private boolean isEligibleForDiscount(OrderInfo orderInfo) {
        int totalPrice = orderInfo.calculateTotalPrice();

        return totalPrice >= MINIMUM_PRICE_FOR_DISCOUNT;
    }

    private void applyDiscounts(OrderInfo orderInfo, Map<DiscountEventType, DiscountedAmount> discountEventResult) {
        discountEventRegistry.forEach((discountType, discountPolicy) -> {
            DiscountedAmount discountedAmount = applyDiscount(orderInfo, discountPolicy);
            discountEventResult.put(discountType, discountedAmount);
        });
    }

    private DiscountedAmount applyDiscount(OrderInfo orderInfo, DiscountPolicy discountPolicy) {
        return discountPolicy.applyDiscount(orderInfo);
    }

}
