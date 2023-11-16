package christmas.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EventScheduleTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 32})
    void 이벤트_기간내의_날짜로_변환하기_위해_숫자를_입력받을떄_날짜_범위를_벗어나면_예외가_발생한다(int day) {
        assertThatThrownBy(() -> EventSchedule.MAIN_EVENT_SEASON.createDateForDay(day))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 31})
    void 이벤트_기간내의_날짜로_변환하기_위해_숫자를_입력받을떄_날짜_범위를_벗어나지_않으면_예외가_발생하지_않는다(int day) {
        assertDoesNotThrow(() -> EventSchedule.MAIN_EVENT_SEASON.createDateForDay(day));
    }
}
