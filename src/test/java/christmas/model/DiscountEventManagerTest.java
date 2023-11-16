package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DiscountEventManagerTest {
    private final DiscountEventManager eventManager = DiscountEventManager.from(
            new EnumMap<>(DiscountEventType.class) {
                {
                    put(DiscountEventType.CHRISTMAS_DDAY_EVENT, new ChristmasDdayDiscount());
                    put(DiscountEventType.WEEKEND_EVENT, new WeekendDiscount());
                    put(DiscountEventType.WEEKDAY_EVENT, new WeekdayDiscount());
                    put(DiscountEventType.SPECIAL_EVENT, new SpecialDiscount());
                    put(DiscountEventType.PROMOTION_EVENT, new PromotionDiscount());
                }
            }
    );

    @Test
    void 주문총액이_할인_이벤트를_받기_위한_최소조건에_미치지_못하는_경우_할인_혜택_내역은_비어있다() {
        VisitDate visitDate = createVisitDate(2023, 12, 10);
        Order order9000won = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order9000won, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult()).isEmpty();
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
    void 주문총액이_할인_이벤트를_받기_위한_최소조건에_만족하는_경우_할인_혜택을_받을_수_있다() {
        VisitDate visitDate = createVisitDate(2023, 12, 10);
        Order order10500won = createOrder(
                createOrderItem(Menu.ICE_CREAM, 2)
        );
        OrderInfo orderInfo = OrderInfo.of(order10500won, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_900),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1_000),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(0)
                ));
    }

    @Test
    void 크리스마스할인_주중할인_특별할인_증정이벤트을_만족하는_12월_3일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 3);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.T_BONE_STEAK, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_200),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1_000),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(25_000)
                ));
    }

    @Test
    void 크리스마스할인_주중할인_증정이벤트을_만족하는_12월_7일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 7);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.T_BONE_STEAK, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_600),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(25_000)
                ));
    }

    @Test
    void 크리스마스할인_주말할인_증정이벤트을_만족하는_12월_8일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 8);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.T_BONE_STEAK, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_700),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(25_000)
                ));
    }

    @Test
    void 크리스마스할인_주말할인을_만족하는_12월_9일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 9);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.CHRISTMAS_PASTA, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 1),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_800),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(0)
                ));
    }

    @Test
    void 크리스마스할인_주중할인_특별할인_증정이벤트을_만족하는_12월_25일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 25);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.T_BONE_STEAK, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);

        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(3_400),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1_000),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(25_000)
                ));
    }

    @Test
    void 주중할인_증정이벤트을_만족하는_12월_28일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 28);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.T_BONE_STEAK, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);
        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);
        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(25_000)
                ));
    }

    @Test
    void 주중할인_특별할인_증정이벤트을_만족하는_12월_31일_주문_할인_테스트() {
        VisitDate visitDate = createVisitDate(2023, 12, 31);
        Order order = createOrder(
                createOrderItem(Menu.MUSHROOM_SOUP, 1),
                createOrderItem(Menu.T_BONE_STEAK, 2),
                createOrderItem(Menu.CHOCOLATE_CAKE, 2),
                createOrderItem(Menu.ZERO_COLA, 1)
        );
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);
        AppliedDiscountEventResult result = eventManager.applyDiscountEvents(orderInfo);
        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1_000),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(25_000)
                ));
    }
}
