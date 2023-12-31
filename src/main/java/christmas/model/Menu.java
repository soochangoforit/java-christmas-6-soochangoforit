package christmas.model;

import java.util.stream.Stream;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", 6_000, Category.APPETIZER),
    TAPAS("타파스", 5_500, Category.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, Category.APPETIZER),

    T_BONE_STEAK("티본스테이크", 55_000, Category.MAIN),
    BBQ_RIBS("바비큐립", 54_000, Category.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, Category.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, Category.MAIN),

    CHOCOLATE_CAKE("초코케이크", 15_000, Category.DESSERT),
    ICE_CREAM("아이스크림", 5_000, Category.DESSERT),

    ZERO_COLA("제로콜라", 3_000, Category.BEVERAGE),
    RED_WINE("레드와인", 60_000, Category.BEVERAGE),
    CHAMPAGNE("샴페인", 25_000, Category.BEVERAGE),

    NONE("없음", 0, Category.NONE);

    private static final String MENU_NOT_FOUND = "[ERROR] 유효하지 않은 주문입니다.";
    private static final String MENU_NAME_IS_BLANK = "[ERROR] 메뉴 이름은 공백일 수 없습니다.";
    private static final String MENU_PRICE_IS_BELOW_MINIMUM = "[ERROR] 메뉴 가격은 %,d원 이상이어야 합니다.";
    private static final int MINIMUM_PRICE = 0;

    private final String name;
    private final int price;
    private final Category category;

    Menu(String name, int price, Category category) {
        validate(name, price);
        this.name = name;
        this.price = price;
        this.category = category;
    }

    private void validate(String name, int price) {
        validateBlank(name);
        validateMinimumPrice(price);
    }

    private void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(MENU_NAME_IS_BLANK);
        }
    }

    private void validateMinimumPrice(int price) {
        if (price < MINIMUM_PRICE) {
            String exceptionMessage = String.format(MENU_PRICE_IS_BELOW_MINIMUM, MINIMUM_PRICE);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static Menu from(String menuName) {
        return Stream.of(values())
                .filter(menu -> menu.name.equals(menuName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MENU_NOT_FOUND));
    }

    public int calculateAmounts(Quantity quantity) {
        return quantity.multiply(price);
    }

    public int calculateDiscountAmountsForPromotion(int quantity) {
        return quantity * price;
    }

    public boolean belongsTo(Category category) {
        return this.category == category;
    }

    public boolean isBeverage() {
        return category == Category.BEVERAGE;
    }

    public String getName() {
        return name;
    }
}
