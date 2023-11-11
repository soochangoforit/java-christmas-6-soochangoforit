package christmas.model;

@FunctionalInterface
public interface DiscountPolicy {
    DiscountedAmount applyDiscount(OrderInfo orderInfo);
}
