package christmas.model;

public enum EventSchedule {
    MAIN_EVENT_SEASON(2023, 12);

    private final int year;
    private final int month;

    EventSchedule(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
