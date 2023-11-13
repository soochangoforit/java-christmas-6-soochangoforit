package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WeekendDiscountTest {

    @ParameterizedTest
    @ValueSource(ints = {8, 9})
    void 주말에_메인과_다른_메뉴를_함께_주문하는_경우_메인_메뉴에_대해서만_할인_혜택을_받는다(int day) {
        WeekendDiscount weekendDiscount = new WeekendDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekendDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(4_046));
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9})
    void 주말에_메인을_주문하는_경우_메인_수량만큼_할인_혜택을_받는다(int day) {
        WeekendDiscount weekendDiscount = new WeekendDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(3))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekendDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(6_069));
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9})
    void 주말에_메인을_주문하지_않는_경우_할인_혜택을_받지_못한다(int day) {
        WeekendDiscount weekendDiscount = new WeekendDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(2))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekendDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 10})
    void 주말_이벤트_기간_벗어나서_메인을_주문하는_경우_할인_혜택을_받지_못한다(int day) {
        WeekendDiscount weekendDiscount = new WeekendDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekendDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }
}
