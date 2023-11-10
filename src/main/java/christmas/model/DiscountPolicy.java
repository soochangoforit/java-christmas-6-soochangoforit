package christmas.model;

@FunctionalInterface
public interface DiscountPolicy {
    DiscountedAmount applyDiscount(OrderResult orderResult);
}
