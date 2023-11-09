package christmas.model;

public class DateOfVisit {
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;

    private final int date;

    private DateOfVisit(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        if (isInvalidDate(date)) {
            throw new IllegalArgumentException("1일부터 31일 사이의 날짜만 입력 가능합니다.");
        }
    }

    private boolean isInvalidDate(int date) {
        return date < MIN_DATE || date > MAX_DATE;
    }

    public static DateOfVisit from(int date) {
        return new DateOfVisit(date);
    }
}
