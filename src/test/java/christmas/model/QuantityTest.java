package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void 유효한_값으로_수량을_생성하면_예외가_발생하지_않는다(int validCount) {
        assertDoesNotThrow(() -> Quantity.from(validCount));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void 유효하지_않은_값으로_수량을_생성하면_예외가_발생한다(int invalidCount) {
        assertThatThrownBy(() -> Quantity.from(invalidCount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("수량_가격_곱셈결과")
    void 수량과_가격을_통해서_곱셈을_할_수_있다(int count, int menuPrice, int expectedTotal) {
        Quantity quantity = Quantity.from(count);

        int total = quantity.multiply(menuPrice);

        assertThat(total).isEqualTo(expectedTotal);
    }

    @Test
    void 수량_객체는_같은_수량_값을_가진_객체와_같다() {
        Quantity quantity = Quantity.from(1);
        Quantity sameQuantity = Quantity.from(1);

        assertThat(quantity).isEqualTo(sameQuantity);
    }

    @Test
    void 수량_객체는_다른_수량_값을_가진_객체와_다르다() {
        Quantity quantity = Quantity.from(1);
        Quantity differentQuantity = Quantity.from(2);

        assertThat(quantity).isNotEqualTo(differentQuantity);
    }

    @Test
    void 수량_객체는_같은_수량_값을_가진_객체와_해시코드가_같다() {
        Quantity quantity = Quantity.from(1);
        Quantity sameQuantity = Quantity.from(1);

        assertThat(quantity).hasSameHashCodeAs(sameQuantity);
    }

    private static Stream<Arguments> 수량_가격_곱셈결과() {
        return Stream.of(
                Arguments.of(1, 10_000, 10_000),
                Arguments.of(3, 5_000, 15_000),
                Arguments.of(5, 20_000, 100_000)
        );
    }
}
