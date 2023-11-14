package christmas.model;

public enum DiscountEventType {
    CHRISTMAS_DDAY_EVENT("크리스마스 디데이 할인", true),
    WEEKDAY_EVENT("평일 할인", true),
    WEEKEND_EVENT("주말 할인", true),
    SPECIAL_EVENT("특별 할인", true),
    PROMOTION_EVENT("증정 이벤트", false);

    private static final String BLANK_DISCOUNT_EVENT_NAME = "할인 이벤트 이름은 공백일 수 없습니다.";

    private final String name;
    private final boolean isOrderAmountsReducible;
    
    DiscountEventType(String name, boolean isOrderAmountsReducible) {
        validateBlank(name);
        this.name = name;
        this.isOrderAmountsReducible = isOrderAmountsReducible;
    }

    private void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(BLANK_DISCOUNT_EVENT_NAME);
        }
    }

    public boolean isOrderAmountsReducible() {
        return this.isOrderAmountsReducible;
    }

    public String getName() {
        return name;
    }
}
