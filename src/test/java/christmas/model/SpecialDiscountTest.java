package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class SpecialDiscountTest {
    private final SpecialDiscount specialDiscount = new SpecialDiscount();

    @ParameterizedTest
    @CsvSource({"2023,11,30", "2024,1,1"})
    void 주문날짜가_12월이_아닌_경우_할인이_적용되지_않는다(int year, int month, int day) {
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1)
        );
        VisitDate visitDate = createVisitDate(year, month, day);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = specialDiscount.applyDiscount(orderInfo);

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
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    void 주문날짜가_특별할인_이벤트_기간이면_할인이_적용된다(int specialDay) {
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1)
        );
        VisitDate visitDate = createVisitDate(2023, 12, specialDay);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = specialDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(1_000));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 9, 11, 16, 18, 23, 26, 30})
    void 주문날짜가_특별할인_이벤트_기간이_아니면_할인이_적용되지_않는다(int notSpecialDay) {
        VisitDate visitDate = createVisitDate(2023, 12, notSpecialDay);
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = specialDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.noDiscount());
    }
}
