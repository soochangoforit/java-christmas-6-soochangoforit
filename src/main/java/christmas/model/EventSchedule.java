package christmas.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public enum EventSchedule {
    MAIN_EVENT_SEASON(2023, 12);

    private static final String INVALID_DATE = "유효하지 않은 날짜입니다.";
    private final int year;
    private final int month;

    EventSchedule(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public LocalDate createDateForDay(int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INVALID_DATE);
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
