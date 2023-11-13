package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasDdayDiscountTest {

    @ParameterizedTest
    @MethodSource("할인_적용_테스트_데이터")
    void 총할인_혜택_금액에_따라_이벤트_배지를_찾을_수_있다(LocalDate orderDate, int expectedDiscountAmount) {
        ChristmasDdayDiscount discountPolicy = new ChristmasDdayDiscount();
        VisitDate visitDate = new VisitDate(orderDate);
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                visitDate
        );

        DiscountAmounts discountAmounts = discountPolicy.applyDiscount(orderInfo);

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(expectedDiscountAmount));
    }

    private static Stream<Arguments> 할인_적용_테스트_데이터() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 1), 1000),
                Arguments.of(LocalDate.of(2023, 12, 5), 1400),
                Arguments.of(LocalDate.of(2023, 12, 25), 3400),
                Arguments.of(LocalDate.of(2023, 12, 26), 0)
        );
    }
}
