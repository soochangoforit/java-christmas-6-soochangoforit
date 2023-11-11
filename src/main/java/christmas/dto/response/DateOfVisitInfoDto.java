package christmas.dto.response;

import java.time.LocalDate;
import christmas.model.VisitDate;

public class DateOfVisitInfoDto {
    private final int month;
    private final int day;

    private DateOfVisitInfoDto(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static DateOfVisitInfoDto from(VisitDate visitDate) {
        LocalDate date = visitDate.getDate();

        return new DateOfVisitInfoDto(date.getMonthValue(), date.getDayOfMonth());
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
