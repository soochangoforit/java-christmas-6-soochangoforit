package christmas.model;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class VisitDate {
    private final LocalDate date;

    public VisitDate(LocalDate date) {
        this.date = date;
    }

    public static VisitDate from(int visitDay) {
        try {
            return new VisitDate(LocalDate.of(2023, 12, visitDay));
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        return isOnOrAfter(startDate) && isOnOrBefore(endDate);
    }

    private boolean isOnOrAfter(LocalDate startDate) {
        return date.isEqual(startDate) || date.isAfter(startDate);
    }

    private boolean isOnOrBefore(LocalDate endDate) {
        return date.isEqual(endDate) || date.isBefore(endDate);
    }

    public boolean isInDays(Set<Integer> eventDays) {
        return eventDays.contains(date.getDayOfMonth());
    }

    public boolean isInDayOfWeeks(List<DayOfWeek> dayOfWeeks) {
        return dayOfWeeks.contains(date.getDayOfWeek());
    }

    public int daysFrom(LocalDate eventStartDate) {
        return eventStartDate.until(date).getDays();
    }

    public LocalDate getDate() {
        return date;
    }
}
