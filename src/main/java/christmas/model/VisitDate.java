package christmas.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class VisitDate {
    private final LocalDate visitDate;

    public VisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public static VisitDate from(int visitDay) {
        try {
            return new VisitDate(LocalDate.of(2023, 12, visitDay));
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }
}
