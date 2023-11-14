package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountEventTypeTest {

    @ParameterizedTest
    @MethodSource("provideDiscountEventTypes")
    void 이벤트_타입이_할인_이벤트인지_확인할_수_있다(DiscountEventType discountEventType, boolean isDiscountEvent) {
        boolean expectedDiscountEvent = discountEventType.isDiscountEvent();

        assertThat(expectedDiscountEvent).isEqualTo(isDiscountEvent);
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
