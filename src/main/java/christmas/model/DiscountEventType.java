package christmas.model;

public enum DiscountEventType {
    CHRISTMAS_DDAY_EVENT("크리스마스 디데이 할인"),
    WEEKDAY_EVENT("평일 할인"),
    WEEKEND_EVENT("주말 할인"),
    SPECIAL_EVENT("특별 할인"),
    PROMOTION_EVENT("증정 이벤트");

    private static final String BLANK_DISCOUNT_EVENT_NAME = "할인 이벤트 이름은 공백일 수 없습니다.";

    private final String name;

    DiscountEventType(String name) {
        validateBlank(name);
        this.name = name;
    }

    private void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(BLANK_DISCOUNT_EVENT_NAME);
        }
    }

    public String getName() {
        return name;
    }
}
