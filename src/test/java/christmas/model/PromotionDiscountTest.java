package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionDiscountTest {
    private final PromotionDiscount promotionDiscount = new PromotionDiscount();

    @ParameterizedTest
    @CsvSource({"2023,11,30", "2024,1,1"})
    void 주문날짜가_12월이_아닌_경우_할인이_적용되지_않는다(int year, int month, int day) {
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1)
        );
        VisitDate visitDate = createVisitDate(year, month, day);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = promotionDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

    private static OrderItem createOrderItem(Menu menu, int quantity) {
        return new OrderItem(menu, Quantity.from(quantity));
    }

    private static Order createOrder(OrderItem... orderItems) {
        return new Order(List.of(orderItems));
    }

    private VisitDate createVisitDate(int year, int month, int day) {
        return VisitDate.from(year, month, day);
    }

    @Test
    void 증정_이벤트를_위한_최소주문금액을_맞추지_못하면_할인이_적용되지_않는다() {
        VisitDate visitDate = createVisitDate(2023, 12, 25);
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1),
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.SEAFOOD_PASTA, 1),
                createOrderItem(Menu.CHRISTMAS_PASTA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = promotionDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

    @Test
    void 증정_이벤트를_위한_최소주문금액을_맞추면_할인이_적용된다() {
        VisitDate visitDate = createVisitDate(2023, 12, 25);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.SEAFOOD_PASTA, 1),
                createOrderItem(Menu.CHRISTMAS_PASTA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = promotionDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(25_000));
    }
}
