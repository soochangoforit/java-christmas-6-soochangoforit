package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public final class DiscountEventManager {
    private static final int MINIMUM_AMOUNT_FOR_DISCOUNT = 10_000;

    private final Map<DiscountEventType, DiscountPolicy> discountEventRegistry;

    private DiscountEventManager(Map<DiscountEventType, DiscountPolicy> discountEventRegistry) {
        this.discountEventRegistry = new EnumMap<>(discountEventRegistry);
    }

    public static DiscountEventManager from(Map<DiscountEventType, DiscountPolicy> discountEventRegistry) {
        return new DiscountEventManager(discountEventRegistry);
    }

    public AppliedDiscountEventResult applyDiscountEvents(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountAmounts> appliedDiscountEventResult = generateDiscountEventResult(orderInfo);

        return AppliedDiscountEventResult.from(appliedDiscountEventResult);
    }

    private Map<DiscountEventType, DiscountAmounts> generateDiscountEventResult(OrderInfo orderInfo) {
        if (isEligibleForDiscount(orderInfo)) {
            return applyDiscounts(orderInfo);
        }

        return emptyDiscountEventResult();
    }

    private boolean isEligibleForDiscount(OrderInfo orderInfo) {
        OrderAmounts orderAmountsBeforeDiscount = orderInfo.calculateOrderAmounts();

        return orderAmountsBeforeDiscount.isEligibleFor(MINIMUM_AMOUNT_FOR_DISCOUNT);
    }

    private Map<DiscountEventType, DiscountAmounts> applyDiscounts(OrderInfo orderInfo) {
        Map<DiscountEventType, DiscountAmounts> appliedDiscountEventResult = new EnumMap<>(DiscountEventType.class);
        discountEventRegistry.forEach((discountEventType, discountPolicy) -> {
            DiscountAmounts discountAmounts = applyDiscountPolicy(discountPolicy, orderInfo);
            appliedDiscountEventResult.put(discountEventType, discountAmounts);
        });

        return appliedDiscountEventResult;
    }

    private DiscountAmounts applyDiscountPolicy(DiscountPolicy discountPolicy, OrderInfo orderInfo) {
        return discountPolicy.applyDiscount(orderInfo);
    }

    private Map<DiscountEventType, DiscountAmounts> emptyDiscountEventResult() {
        return new EnumMap<>(DiscountEventType.class);
    }
}
