package christmas.model;

import java.time.LocalDate;

public enum EventSchedule {
    MAIN_EVENT_SEASON(2023, 12);

    private final int year;
    private final int month;

    EventSchedule(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public LocalDate getStartDate(int day) {
        return LocalDate.of(year, month, day);
    }

    public LocalDate getEndDate(int day) {
        return LocalDate.of(year, month, day);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
