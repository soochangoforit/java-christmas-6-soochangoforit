package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderInfoTest {

    @Test
    void 주문과_방문날짜를_통해서_주문정보를_생성할_수_있다() {
        VisitDate date = VisitDate.from(2023, 12, 25);
        Order order = new Order(List.of(new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1))));

        OrderInfo orderInfo = OrderInfo.of(order, date);

        assertThat(orderInfo).isNotNull();
    }

    @Test
    void 주문_총금액을_계산할_수_있다() {
        VisitDate date = VisitDate.from(2023, 12, 25);
        Order order = new Order(List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
        ));
        OrderInfo orderInfo = OrderInfo.of(order, date);

        OrderAmounts orderAmounts = orderInfo.calculateOrderAmounts();

        assertThat(orderAmounts).usingRecursiveComparison()
                .isEqualTo(OrderAmounts.from(70_000));
    }

    @ParameterizedTest
    @MethodSource("방문_날짜가_시작날짜와_종료날짜에_포함되는지_테스트_케이스")
    void 주문날짜가_시작날짜와_종료날짜에_포함되면_참을_반환한다(VisitDate visitDate, boolean expected) {
        LocalDate startDate = LocalDate.of(2023, 12, 25);
        LocalDate endDate = LocalDate.of(2023, 12, 30);
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                visitDate
        );

        boolean isOrderedIn = orderInfo.isOrderedBetween(startDate, endDate);

        assertThat(isOrderedIn).isEqualTo(expected);
    }

    @Test
    void 주문날짜가_특정한_시작날짜로부터_며칠_지났는지_계산할_수_있다() {
        LocalDate startDate = LocalDate.of(2023, 12, 25);
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                VisitDate.from(2023, 12, 27)
        );

        int daysFrom = orderInfo.daysSinceStartDate(startDate);

        assertThat(daysFrom).isEqualTo(2);
    }

    @Test
    void 주문날짜가_특정한_요일에_속하면_참을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                VisitDate.from(2023, 12, 27)
        );

        boolean isInDayOfWeek = orderInfo.isOrderedInDaysOfWeek(Set.of(DayOfWeek.WEDNESDAY));

        assertThat(isInDayOfWeek).isTrue();
    }

    @Test
    void 주문날짜가_특정한_요일에_속하지_않으면_거짓을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                VisitDate.from(2023, 12, 27)
        );

        boolean isInDayOfWeek = orderInfo.isOrderedInDaysOfWeek(Set.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));

        assertThat(isInDayOfWeek).isFalse();
    }

    @Test
    void 주문날짜가_특정_일수에_속하면_참을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                VisitDate.from(2023, 12, 27)
        );

        boolean isInDays = orderInfo.isOrderedInDays(Set.of(27));

        assertThat(isInDays).isTrue();
    }

    @Test
    void 주문날짜가_특정_일수에_속하지_않으면_거짓을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)))),
                VisitDate.from(2023, 12, 27)
        );

        boolean isInDays = orderInfo.isOrderedInDays(Set.of(25, 26, 28, 29, 30));

        assertThat(isInDays).isFalse();
    }

    @Test
    void 주문에_포함된_특정_카테고리의_주문_항목_수량을_찾을_수_없으면_0을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, 27)
        );

        int totalOrderItemQuantityIn = orderInfo.sumTotalOrderItemQuantityIn(Category.BEVERAGE);

        assertThat(totalOrderItemQuantityIn).isEqualTo(0);
    }

    @Test
    void 주문에_포함된_특정_카테고리의_주문_항목_수량을_찾을_수_있다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, 27)
        );

        int totalOrderItemQuantityIn = orderInfo.sumTotalOrderItemQuantityIn(Category.MAIN);

        assertThat(totalOrderItemQuantityIn).isEqualTo(2);
    }

    @Test
    void 주문이_특정_프로모션_받을_자격이_되면_참을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, 27)
        );

        boolean isEligibleForPromotion = orderInfo.isEligibleFor(PromotionItem.FREE_CHAMPAGNE);

        assertThat(isEligibleForPromotion).isTrue();
    }

    @Test
    void 주문이_특정_프로모션_받을_자격이_되지_않으면_거짓을_반환한다() {
        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.BBQ_RIBS, Quantity.from(1)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, 27)
        );

        boolean isEligibleForPromotion = orderInfo.isEligibleFor(PromotionItem.FREE_CHAMPAGNE);

        assertThat(isEligibleForPromotion).isFalse();
    }

    private static Stream<Arguments> 방문_날짜가_시작날짜와_종료날짜에_포함되는지_테스트_케이스() {
        return Stream.of(
                Arguments.of(VisitDate.from(2023, 12, 24), false),
                Arguments.of(VisitDate.from(2023, 12, 25), true),
                Arguments.of(VisitDate.from(2023, 12, 27), true),
                Arguments.of(VisitDate.from(2023, 12, 30), true),
                Arguments.of(VisitDate.from(2023, 12, 31), false)
        );
    }
}
