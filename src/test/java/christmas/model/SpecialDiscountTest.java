package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SpecialDiscountTest {

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    void 주문날짜가_특별할인_이벤트_기간이면_할인이_적용된다(int day) {
        SpecialDiscount specialDiscount = new SpecialDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.TAPAS, Quantity.from(1)))),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = specialDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(1_000));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 9, 11, 16, 18, 23, 26, 30})
    void 주문날짜가_특별할인_이벤트_기간이_아니면_할인이_적용되지_않는다(int day) {
        SpecialDiscount specialDiscount = new SpecialDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.TAPAS, Quantity.from(1)))),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = specialDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

}
