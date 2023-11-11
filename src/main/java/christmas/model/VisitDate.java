package christmas.model;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public final class VisitDate {
    private static final String INVALID_DATE = "유효하지 않은 날짜입니다.";

    private final LocalDate date;

    VisitDate(LocalDate date) {
        this.date = date;
    }

    public static VisitDate from(int year, int month, int day) {
        LocalDate localDate = createLocalDate(year, month, day);

        return new VisitDate(localDate);
    }

    private static LocalDate createLocalDate(int year, int month, int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INVALID_DATE);
        }
    }

    public int daysSince(LocalDate startDate) {
        return (int) startDate.datesUntil(date)
                .count();
    }

    public boolean isInDayOfWeek(Set<DayOfWeek> daysOfWeek) {
        return daysOfWeek.contains(date.getDayOfWeek());
    }

    public boolean isInDays(Set<Integer> days) {
        return days.contains(date.getDayOfMonth());
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        return isSince(startDate) && isUntil(endDate);
    }

    private boolean isSince(LocalDate startDate) {
        return date.isEqual(startDate) || date.isAfter(startDate);
    }

    private boolean isUntil(LocalDate endDate) {
        return date.isEqual(endDate) || date.isBefore(endDate);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisitDate visitDate = (VisitDate) o;
        return Objects.equals(date, visitDate.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
