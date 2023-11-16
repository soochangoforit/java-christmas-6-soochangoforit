package christmas.model;

@FunctionalInterface
public interface DiscountPolicy {
    DiscountAmounts applyDiscount(OrderInfo orderInfo);
}
