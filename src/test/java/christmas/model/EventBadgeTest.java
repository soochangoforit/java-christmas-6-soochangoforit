package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventBadgeTest {

    @ParameterizedTest
    @MethodSource("이벤트_배지_찾기_테스트_데이터")
    void 총할인_혜택_금액에_따라_이벤트_배지를_찾을_수_있다(TotalDiscountAmounts totalDiscountAmounts,
                                       EventBadge expectedEventBadge) {
        EventBadge actualEventBadge = EventBadge.findMatchingEventBadge(totalDiscountAmounts);

        assertThat(actualEventBadge).isEqualTo(expectedEventBadge);
    }

    @Test
    void 서로_같은_값을_가지는_이벤트_배지는_같은_객체이다() {
        EventBadge eventBadge = EventBadge.SANTA;
        EventBadge sameEventBadge = EventBadge.SANTA;

        assertThat(eventBadge).isSameAs(sameEventBadge);
    }

    @Test
    void 서로_다른_값을_가지는_이벤트_배지는_다른_객체이다() {
        EventBadge eventBadge = EventBadge.SANTA;
        EventBadge differentEventBadge = EventBadge.TREE;

        assertThat(eventBadge).isNotSameAs(differentEventBadge);
    }

    @Test
    void 서로_같은_값을_가지는_이벤트_배지는_같은_해시코드를_가진다() {
        EventBadge eventBadge = EventBadge.SANTA;
        EventBadge sameEventBadge = EventBadge.SANTA;

        assertThat(eventBadge).hasSameHashCodeAs(sameEventBadge);
    }

    private static Stream<Arguments> 이벤트_배지_찾기_테스트_데이터() {
        return Stream.of(
                Arguments.of(TotalDiscountAmounts.from(20_000), EventBadge.SANTA),
                Arguments.of(TotalDiscountAmounts.from(10_000), EventBadge.TREE),
                Arguments.of(TotalDiscountAmounts.from(5_000), EventBadge.STAR),
                Arguments.of(TotalDiscountAmounts.from(500), EventBadge.NONE)
        );
    }
}
