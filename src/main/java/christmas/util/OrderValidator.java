package christmas.util;

import java.util.regex.Pattern;

public class OrderValidator {
    private static final Pattern ORDER_ITEM_PATTERN = Pattern.compile("([가-힣]+-[0-9]+)(,[가-힣]+-[0-9]+)*");

    private OrderValidator() {
    }

    public static void validate(String input) {
        if (!matchesPattern(input, ORDER_ITEM_PATTERN)) {
            throw new IllegalArgumentException("{메뉴 이름}-{수량} 형식으로 입력해 주세요.");
        }
    }

    private static boolean matchesPattern(String input, Pattern pattern) {
        return pattern.matcher(input).matches();
    }
}
