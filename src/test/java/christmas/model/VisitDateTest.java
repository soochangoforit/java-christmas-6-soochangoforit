package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 32})
    void 유효하지_않은_방문_날짜를_입력하면_예외가_발생한다(int day) {
        assertThatThrownBy(() -> VisitDate.from(2023, 12, day))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 31})
    void 유효한_날짜를_입력하면_예외가_발생하지_않는다(int day) {
        assertDoesNotThrow(() -> VisitDate.from(2023, 12, day));
    }

    @ParameterizedTest
    @MethodSource("방문날짜_지나온_일수")
    void 특정날짜로부터_방문날짜까지_일수를_구할_수_있다(int visitDay, int expectedDaysSinceCount) {
        VisitDate visitDate = VisitDate.from(2023, 12, visitDay);
        LocalDate startDate = LocalDate.of(2023, 12, 1);

        int actualDaysSinceCount = visitDate.daysSince(startDate);

        assertThat(actualDaysSinceCount).isEqualTo(expectedDaysSinceCount);
    }

    @ParameterizedTest
    @MethodSource("방문일자_특정요일_특정요일에_속한_여부")
    void 방문날짜가_특정요일에_속하는지_알_수_있다(LocalDate date, Set<DayOfWeek> dayOfWeeks, boolean expectedResult) {
        VisitDate visitDate = new VisitDate(date);

        boolean actualResult = visitDate.isInDayOfWeek(dayOfWeeks);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("방문날짜_특정일수_매칭여부")
    void 방문날짜가_특정일수에_포함되는지_알_수_있다(LocalDate date, Set<Integer> days, boolean expectedResult) {
        VisitDate visitDate = new VisitDate(date);

        boolean actualResult = visitDate.isInDays(days);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("방문날짜_시작날짜_종료날짜_기간범위_포함_여부")
    void 방문날짜가_시작날짜와_종료날짜_사이에_포함되는지_알_수_있다(LocalDate date, LocalDate startDate, LocalDate endDate,
                                           boolean expectedResult) {
        VisitDate visitDate = new VisitDate(date);

        boolean actualResult = visitDate.isBetween(startDate, endDate);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void 날짜가_같은_방문날짜는_서로_같은_객체이다() {
        VisitDate visitDate = VisitDate.from(2023, 12, 1);
        VisitDate sameVisitDate = VisitDate.from(2023, 12, 1);

        assertThat(visitDate).isEqualTo(sameVisitDate);
    }

    @Test
    void 날짜가_다른_방문날짜는_서로_다른_객체이다() {
        VisitDate visitDate = VisitDate.from(2023, 12, 1);
        VisitDate differentVisitDate = VisitDate.from(2023, 12, 2);

        assertThat(visitDate).isNotEqualTo(differentVisitDate);
    }

    @Test
    void 날짜가_같은_방문날짜는_서로_같은_해시코드를_가진다() {
        VisitDate visitDate = VisitDate.from(2023, 12, 1);
        VisitDate sameVisitDate = VisitDate.from(2023, 12, 1);

        assertThat(visitDate).hasSameHashCodeAs(sameVisitDate);
    }

    private static Stream<Arguments> 방문날짜_시작날짜_종료날짜_기간범위_포함_여부() {
        LocalDate startDate = java.time.LocalDate.of(2023, 12, 2);
        LocalDate endDate = LocalDate.of(2023, 12, 30);

        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 1), startDate, endDate, false),
                Arguments.of(LocalDate.of(2023, 12, 2), startDate, endDate, true),
                Arguments.of(LocalDate.of(2023, 12, 30), startDate, endDate, true),
                Arguments.of(LocalDate.of(2023, 12, 31), startDate, endDate, false)
        );
    }

    private static Stream<Arguments> 방문날짜_특정일수_매칭여부() {
        return java.util.stream.Stream.of(
                Arguments.of(
                        LocalDate.of(2023, 12, 1),
                        Set.of(1, 2, 3),
                        true),
                Arguments.of(
                        LocalDate.of(2023, 12, 15),
                        Set.of(1, 2, 3),
                        false),
                Arguments.of(
                        LocalDate.of(2023, 12, 20),
                        Collections.emptySet(),
                        false)
        );
    }

    private static Stream<Arguments> 방문일자_특정요일_특정요일에_속한_여부() {
        LocalDate friday = LocalDate.of(2023, 12, 1);
        LocalDate saturday = LocalDate.of(2023, 12, 2);
        LocalDate sunday = LocalDate.of(2023, 12, 3);

        return Stream.of(
                Arguments.of(
                        friday,
                        EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
                        true),
                Arguments.of(
                        saturday,
                        EnumSet.of(DayOfWeek.FRIDAY),
                        false),
                Arguments.of(
                        sunday,
                        EnumSet.noneOf(DayOfWeek.class),
                        false)
        );
    }

    private static Stream<Arguments> 방문날짜_지나온_일수() {
        return Stream.of(
                Arguments.of(1, 0),
                Arguments.of(2, 1),
                Arguments.of(15, 14)
        );
    }
}
