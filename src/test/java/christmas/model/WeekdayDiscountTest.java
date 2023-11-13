package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WeekdayDiscountTest {

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 주중_이벤트_기간에_다른_카테고리_메뉴와_디저트를_함께_주문하는_경우_디저트_항목만_주중할인_혜택을_받을_수_있다(int day) {
        WeekdayDiscount weekdayDiscount = new WeekdayDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                        new OrderItem(Menu.ICE_CREAM, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(4_046));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 주중_이벤트_기간에_디저트를_주문하는_경우_디저트_수량만큼_주중할인_혜택을_받을_수_있다(int day) {
        WeekdayDiscount weekdayDiscount = new WeekdayDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(2)),
                        new OrderItem(Menu.ICE_CREAM, Quantity.from(3))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(10_115));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 주중_이벤트_기간에_디저트를_주문하지_않는_경우_주중할인_혜택을_받을_수_없다(int day) {
        WeekdayDiscount weekdayDiscount = new WeekdayDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9})
    void 주중_이벤트_기간_벗어나서_디저트를_주문하는_경우_주중할인_혜택을_받을_수_없다(int day) {
        WeekdayDiscount weekdayDiscount = new WeekdayDiscount();
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                        new OrderItem(Menu.ICE_CREAM, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, day)
        );

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }
}
