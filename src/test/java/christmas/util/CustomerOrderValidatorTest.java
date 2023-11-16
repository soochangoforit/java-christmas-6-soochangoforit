package christmas.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerOrderValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"케이크-1:음료-1", "케이크,1,음료,1", "케이크:1,음료:1", "케이크,1:음료,1"})
    void 올바른_형식으로_주문을_입력하지_않는_경우_예외가_발생한다(String input) {
        assertThatThrownBy(() -> CustomerOrderValidator.validate(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"케이크-1,음료-1", "버거-1,콜라-1"})
    void 올바른_형식으로_주문을_입력하는_경우_예외가_발생하지_않는다(String input) {
        assertDoesNotThrow(() -> CustomerOrderValidator.validate(input));
    }
}
