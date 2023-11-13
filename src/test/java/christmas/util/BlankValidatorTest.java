package christmas.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BlankValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 공백_혹은_빈문자열을_입력하면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> BlankValidator.validate(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "가", "1"})
    void 공백_혹은_빈문자열이_아닌_경우_예외가_발생하지_않는다(String input) {
        assertDoesNotThrow(() -> BlankValidator.validate(input));
    }
}
