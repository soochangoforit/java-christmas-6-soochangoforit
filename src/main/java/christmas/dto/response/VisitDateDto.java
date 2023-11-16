package christmas.dto.response;

import java.time.LocalDate;
import christmas.model.VisitDate;

public class VisitDateDto {
    private final int month;
    private final int day;

    private VisitDateDto(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static VisitDateDto from(VisitDate visitDate) {
        LocalDate date = visitDate.getDate();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return new VisitDateDto(month, day);
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
