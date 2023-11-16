package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasDdayDiscountTest {
    private final ChristmasDdayDiscount christmasDdayDiscount = new ChristmasDdayDiscount();

    @ParameterizedTest
    @CsvSource(value = {"2023-11-30", "2023-12-26"}, delimiter = '-')
    void 주문날짜가_이벤트_기간이_아닌_경우_할인이_적용되지_않는다(int year, int month, int day) {
        Order order = createOrder(
                createOrderItem(Menu.TAPAS, 1)
        );
        VisitDate visitDate = createVisitDate(year, month, day);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = christmasDdayDiscount.applyDiscount(orderInfo);

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
    @MethodSource("할인_적용_테스트_데이터")
    void 주문날짜가_이벤트_기간에_속하고_날짜에_따라_할인금액이_달라진다(int day, int expectedDiscountAmount) {
        VisitDate visitDate = createVisitDate(2023, 12, day);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        DiscountAmounts discountAmounts = christmasDdayDiscount.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(expectedDiscountAmount));
    }

    private static Stream<Arguments> 할인_적용_테스트_데이터() {
        return Stream.of(
                Arguments.of(1, 1_000),
                Arguments.of(5, 1_400),
                Arguments.of(25, 3_400),
                Arguments.of(26, 0)
        );
    }
}
