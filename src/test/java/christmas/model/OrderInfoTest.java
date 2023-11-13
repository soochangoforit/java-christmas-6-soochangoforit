package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderInfoTest {

    @Test
    void 주문과_방문날짜를_통해서_주문정보를_생성할_수_있다() {
        VisitDate date = createVisitDate(2023, 12, 25);
        Order order = createOrder(
                createOrderItem(Menu.CHOCOLATE_CAKE, 1)
        );

        OrderInfo orderInfo = OrderInfo.of(order, date);

        assertThat(orderInfo).isNotNull();
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
    void 주문_총금액을_계산할_수_있다() {
        VisitDate date = createVisitDate(2023, 12, 25);
        Order order = createOrder(
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.T_BONE_STEAK, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, date);

        OrderAmounts orderAmounts = orderInfo.calculateOrderAmounts();

        assertThat(orderAmounts).usingRecursiveComparison()
                .isEqualTo(OrderAmounts.from(70_000));
    }

    @ParameterizedTest
    @ValueSource(ints = {25, 27, 30})
    void 주문날짜가_시작날짜와_종료날짜에_포함되면_참을_반환한다(int visitDay) {
        LocalDate startDate = createLocalDate(2023, 12, 25);
        LocalDate endDate = createLocalDate(2023, 12, 30);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        VisitDate visitDate = createVisitDate(2023, 12, visitDay);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        boolean isOrderedIn = orderInfo.isOrderedBetween(startDate, endDate);

        assertThat(isOrderedIn).isTrue();
    }

    private static LocalDate createLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 31})
    void 주문날짜가_시작날짜와_종료날짜에_포함되지_않으면_거짓을_반환한다(int visitDay) {
        LocalDate startDate = createLocalDate(2023, 12, 25);
        LocalDate endDate = createLocalDate(2023, 12, 30);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        VisitDate visitDate = createVisitDate(2023, 12, visitDay);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        boolean isOrderedIn = orderInfo.isOrderedBetween(startDate, endDate);

        assertThat(isOrderedIn).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"25,27,2", "27,27,0"})
    void 주문날짜가_특정_시작날짜로부터_며칠_지났는지_계산할_수_있다(int startDay, int visitDay, int expectedDaysFrom) {
        LocalDate startDate = createLocalDate(2023, 12, startDay);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        VisitDate visitDate = createVisitDate(2023, 12, visitDay);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        int daysFrom = orderInfo.daysSinceStartDate(startDate);

        assertThat(daysFrom).isEqualTo(expectedDaysFrom);
    }

    @Test
    void 주문날짜가_특정_요일에_속하면_참을_반환한다() {
        VisitDate visitDateIsWednesday = createVisitDate(2023, 12, 27);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDateIsWednesday);

        boolean isInDayOfWeek = orderInfo.isOrderedInDaysOfWeek(Set.of(DayOfWeek.WEDNESDAY));

        assertThat(isInDayOfWeek).isTrue();
    }

    @Test
    void 주문날짜가_특정한_요일에_속하지_않으면_거짓을_반환한다() {
        VisitDate visitDateIsWednesday = createVisitDate(2023, 12, 27);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDateIsWednesday);

        boolean isInDayOfWeek = orderInfo.isOrderedInDaysOfWeek(Set.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));

        assertThat(isInDayOfWeek).isFalse();
    }

    @Test
    void 주문날짜가_특정_일수에_속하면_참을_반환한다() {
        VisitDate visitDate = createVisitDate(2023, 12, 27);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        boolean isInDays = orderInfo.isOrderedInDays(Set.of(27));

        assertThat(isInDays).isTrue();
    }

    @Test
    void 주문날짜가_특정_일수에_속하지_않으면_거짓을_반환한다() {
        VisitDate visitDate = createVisitDate(2023, 12, 27);
        Order order = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        boolean isInDays = orderInfo.isOrderedInDays(Set.of(25, 26, 28, 29, 30));

        assertThat(isInDays).isFalse();
    }

    @Test
    void 주문에_포함된_특정_카테고리의_주문_항목_수량을_찾을_수_없으면_0을_반환한다() {
        VisitDate visitDate = createVisitDate(2023, 12, 27);
        Order orderHasNoBeverageMenu = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.T_BONE_STEAK, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(orderHasNoBeverageMenu, visitDate);

        int totalOrderItemQuantityIn = orderInfo.sumTotalOrderItemQuantityIn(Category.BEVERAGE);

        assertThat(totalOrderItemQuantityIn).isEqualTo(0);
    }

    @Test
    void 주문에_포함된_특정_카테고리의_주문_항목_수량을_찾을_수_있다() {
        VisitDate visitDate = createVisitDate(2023, 12, 27);
        Order orderHasMainMenu = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.T_BONE_STEAK, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(orderHasMainMenu, visitDate);

        int totalOrderItemQuantityIn = orderInfo.sumTotalOrderItemQuantityIn(Category.MAIN);

        assertThat(totalOrderItemQuantityIn).isEqualTo(2);
    }

    @Test
    void 주문이_특정_프로모션_받을_자격이_되면_참을_반환한다() {
        VisitDate visitDate = createVisitDate(2023, 12, 27);
        Order orderIsEligibleForPromotion = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.T_BONE_STEAK, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(orderIsEligibleForPromotion, visitDate);

        boolean isEligibleForPromotion = orderInfo.isEligibleFor(PromotionItem.FREE_CHAMPAGNE);

        assertThat(isEligibleForPromotion).isTrue();
    }

    @Test
    void 주문이_특정_프로모션_받을_자격이_되지_않으면_거짓을_반환한다() {
        VisitDate visitDate = createVisitDate(2023, 12, 27);
        Order orderIsNotEligibleForPromotion = createOrder(
                createOrderItem(Menu.BBQ_RIBS, 1),
                createOrderItem(Menu.CHOCOLATE_CAKE, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(orderIsNotEligibleForPromotion, visitDate);

        boolean isEligibleForPromotion = orderInfo.isEligibleFor(PromotionItem.FREE_CHAMPAGNE);

        assertThat(isEligibleForPromotion).isFalse();
    }
}
