package christmas.dto.response;

import christmas.model.EventBadge;
import christmas.model.EventSchedule;

public class EventBadgeDto {
    private final int eventMonth;
    private final String badgeName;

    private EventBadgeDto(int eventMonth, String badgeName) {
        this.eventMonth = eventMonth;
        this.badgeName = badgeName;
    }

    public static EventBadgeDto from(EventBadge eventBadge) {
        String badgeName = eventBadge.getName();
        int eventMonth = EventSchedule.MAIN_EVENT_SEASON.getMonth();

        return new EventBadgeDto(eventMonth, badgeName);
    }

    public String getBadgeName() {
        return badgeName;
    }

    public int getEventMonth() {
        return eventMonth;
    }
}
