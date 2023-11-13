package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class WeekdayDiscountTest {
    private final WeekdayDiscount weekdayDiscount = new WeekdayDiscount();

    @ParameterizedTest
    @CsvSource({"2023,11,30", "2024,1,1"})
    void 주문날짜가_12월이_아닌_경우_할인이_적용되지_않는다(int year, int month, int day) {
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1)
        );
        VisitDate visitDate = createVisitDate(year, month, day);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

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

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 주중_이벤트_기간에_다른_카테고리_메뉴와_디저트를_함께_주문하는_경우_디저트_항목만_주중할인_혜택을_받을_수_있다(int weekday) {
        VisitDate visitDate = createVisitDate(2023, 12, weekday);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.ICE_CREAM, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(4_046));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 주중_이벤트_기간에_디저트를_주문하는_경우_디저트_수량만큼_주중할인_혜택을_받을_수_있다(int weekday) {
        Order order = createOrder(
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ICE_CREAM, 3)
        );
        VisitDate visitDate = createVisitDate(2023, 12, weekday);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(10_115));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 주중_이벤트_기간에_디저트를_주문하지_않는_경우_주중할인_혜택을_받을_수_없다(int weekday) {
        Order order = createOrder(
                createOrderItem(Menu.T_BONE_STEAK, 1)
        );
        VisitDate visitDate = createVisitDate(2023, 12, weekday);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9})
    void 주중_이벤트_기간_벗어나서_디저트를_주문하는_경우_주중할인_혜택을_받을_수_없다(int weekend) {
        Order order = createOrder(
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.ICE_CREAM, 1)
        );
        VisitDate visitDate = createVisitDate(2023, 12, weekend);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = weekdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }
}
