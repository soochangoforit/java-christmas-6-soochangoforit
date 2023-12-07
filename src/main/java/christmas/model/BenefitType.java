package christmas.model;

public enum BenefitType {
    CHRISTMAS_DISCOUNT_EVENT("크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT_EVENT("평일 할인"),
    WEEKEND_DISCOUNT_EVENT("주말 할인"),
    SPECIAL_DISCOUNT_EVENT("특별 할인"),
    PROMOTION_EVENT("증정 이벤트");

    private final String name;

    BenefitType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
