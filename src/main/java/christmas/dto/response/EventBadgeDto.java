package christmas.dto.response;

import christmas.model.EventBadge;

public class EventBadgeDto {
    private final int eventMonth;
    private final String badgeName;

    private EventBadgeDto(int eventMonth, String badgeName) {
        this.eventMonth = eventMonth;
        this.badgeName = badgeName;
    }

    public static EventBadgeDto from(int eventMonth, EventBadge eventBadge) {
        String badgeName = eventBadge.getName();

        return new EventBadgeDto(eventMonth, badgeName);
    }

    public String getBadgeName() {
        return badgeName;
    }

    public int getEventMonth() {
        return eventMonth;
    }
}
