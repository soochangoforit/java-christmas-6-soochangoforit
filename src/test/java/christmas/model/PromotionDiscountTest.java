package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionDiscountTest {

    /**
     * 119,500
     */
    @Test
    void 증정_이벤트를_위한_최소주문금액을_맞추지_못하면_할인이_적용되지_않는다() {
        PromotionDiscount promotionDiscount = new PromotionDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.TAPAS, Quantity.from(1)),
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.SEAFOOD_PASTA, Quantity.from(1)),
                        new OrderItem(Menu.CHRISTMAS_PASTA, Quantity.from(1)))),
                VisitDate.from(2023, 12, 25)
        );

        DiscountAmounts discountAmounts = promotionDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

    /**
     * 120,000
     */
    @Test
    void 증정_이벤트를_위한_최소주문금액을_맞추면_할인이_적용된다() {
        PromotionDiscount promotionDiscount = new PromotionDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.SEAFOOD_PASTA, Quantity.from(1)),
                        new OrderItem(Menu.CHRISTMAS_PASTA, Quantity.from(1)))),
                VisitDate.from(2023, 12, 25)
        );

        DiscountAmounts discountAmounts = promotionDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(25_000));
    }
}
