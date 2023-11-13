package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountEventManagerTest {

    @Test
    void 주문총액이_할인_이벤트를_받기_위한_최소조건에_미치지_못하는_경우_할인_혜택을_받을_수_없다() {
        Map<DiscountEventType, DiscountPolicy> registry = new EnumMap<>(DiscountEventType.class) {
            {
                put(DiscountEventType.WEEKDAY_EVENT, new WeekdayDiscount());
            }
        };
        DiscountEventManager manager = DiscountEventManager.from(registry);

        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                        new OrderItem(Menu.ZERO_COLA, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, 10)
        );

        AppliedDiscountEventResult result = manager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult()).isEmpty();
    }

    @Test
    void 주문총액이_할인_이벤트를_받기_위한_최소조건에_만족하는_경우_할인_혜택을_받을_수_있다() {
        Map<DiscountEventType, DiscountPolicy> registry = new EnumMap<>(DiscountEventType.class) {
            {
                put(DiscountEventType.WEEKDAY_EVENT, new WeekdayDiscount());
            }
        };
        DiscountEventManager manager = DiscountEventManager.from(registry);

        OrderInfo orderInfo = OrderInfo.of(
                new Order(List.of(
                        new OrderItem(Menu.TAPAS, Quantity.from(1)),
                        new OrderItem(Menu.ICE_CREAM, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, 10)
        );

        AppliedDiscountEventResult result = manager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(2_023)
                ));
    }

    @ParameterizedTest
    @MethodSource("날짜별_할인금액_제공")
    void 주문총액이_할인_이벤트를_받기_위한_최소조건에_만족하고_복수할인이_적용될_수_있다(int day, int christmasDiscountAmounts,
                                                       int weekendDiscountAmounts, int weekdayDiscountAmounts,
                                                       int specialDiscountAmounts, int promotionDiscountAmounts) {
        Map<DiscountEventType, DiscountPolicy> registry = new EnumMap<>(DiscountEventType.class) {
            {
                put(DiscountEventType.CHRISTMAS_DDAY_EVENT, new ChristmasDdayDiscount());
                put(DiscountEventType.WEEKEND_EVENT, new WeekendDiscount());
                put(DiscountEventType.WEEKDAY_EVENT, new WeekdayDiscount());
                put(DiscountEventType.SPECIAL_EVENT, new SpecialDiscount());
                put(DiscountEventType.PROMOTION_EVENT, new PromotionDiscount());
            }
        };
        DiscountEventManager manager = DiscountEventManager.from(registry);

        OrderInfo orderInfo = OrderInfo.of(
                new Order(Arrays.asList(
                        new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2)),
                        new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(2)),
                        new OrderItem(Menu.ZERO_COLA, Quantity.from(1))
                )),
                VisitDate.from(2023, 12, day)
        );

        AppliedDiscountEventResult result = manager.applyDiscountEvents(orderInfo);

        assertThat(result.getDiscountEventResult())
                .containsAllEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(christmasDiscountAmounts),
                        DiscountEventType.WEEKEND_EVENT, DiscountAmounts.from(weekendDiscountAmounts),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(weekdayDiscountAmounts),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(specialDiscountAmounts),
                        DiscountEventType.PROMOTION_EVENT, DiscountAmounts.from(promotionDiscountAmounts)
                ));
    }

    private static Stream<Arguments> 날짜별_할인금액_제공() {
        return Stream.of(
                Arguments.of(1, 1_000, 4_046, 0, 0, 25_000),
                Arguments.of(2, 1_100, 4_046, 0, 0, 25_000),
                Arguments.of(3, 1_200, 0, 4_046, 1_000, 25_000),
                Arguments.of(7, 1_600, 0, 4_046, 0, 25_000),
                Arguments.of(25, 3_400, 0, 4_046, 1_000, 25_000),
                Arguments.of(29, 0, 4_046, 0, 0, 25_000)
        );
    }

}
