package christmas.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDayValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "123abc", "123한글"})
    void 방문할_날짜가_숫자가_아닌_경우_예외가_발생한다(String input) {
        assertThatThrownBy(() -> VisitDayValidator.validate(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "31"})
    void 방문할_날짜가_숫자인_경우_예외가_발생하지_않는다(String input) {
        assertDoesNotThrow(() -> VisitDayValidator.validate(input));
    }
}
