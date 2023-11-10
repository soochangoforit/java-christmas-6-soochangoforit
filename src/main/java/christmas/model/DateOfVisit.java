package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class DateOfVisit {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;

    private final LocalDate date;

    private DateOfVisit(int date) {
        validate(date);
        this.date = LocalDate.of(YEAR, MONTH, date);
    }

    private void validate(int date) {
        if (isInvalidDate(date)) {
            throw new IllegalArgumentException("1일부터 31일 사이의 날짜만 입력 가능합니다.");
        }
    }

    private boolean isInvalidDate(int date) {
        return date < MIN_DATE || date > MAX_DATE;
    }

    public static DateOfVisit from(int dayOfMonth) {

        return new DateOfVisit(dayOfMonth);
    }

    public boolean isOrderedIn(EnumSet<DayOfWeek> weekdays) {
        return weekdays.contains(date.getDayOfWeek());
    }

    public boolean isOrderedIn(Set<Integer> specialDays) {
        return specialDays.contains(date.getDayOfMonth());
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }

    public LocalDate getDate() {
        return date;
    }
}
