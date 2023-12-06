package christmas.model;

import java.time.DateTimeException;
import java.time.LocalDate;

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

    public LocalDate getDate() {
        return date;
    }
}
