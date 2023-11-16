package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountEventTypeTest {

    @ParameterizedTest
    @MethodSource("provideDiscountEventTypes")
    void 이벤트_할인_금액이_총주문금액을_차감할_수_있을지_확인할_수_있다(
            DiscountEventType eventType, boolean isOrderAmountsReducible
    ) {
        boolean expectedDiscountEvent = eventType.isOrderAmountsReducible();

        assertThat(expectedDiscountEvent).isEqualTo(isOrderAmountsReducible);
    }

    private static Stream<Arguments> provideDiscountEventTypes() {
        return Stream.of(
                Arguments.of(DiscountEventType.CHRISTMAS_DDAY_EVENT, true),
                Arguments.of(DiscountEventType.WEEKDAY_EVENT, true),
                Arguments.of(DiscountEventType.WEEKEND_EVENT, true),
                Arguments.of(DiscountEventType.SPECIAL_EVENT, true),
                Arguments.of(DiscountEventType.PROMOTION_EVENT, false)
        );
    }
}
