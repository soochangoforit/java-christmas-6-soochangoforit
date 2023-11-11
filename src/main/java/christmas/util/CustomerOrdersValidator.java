package christmas.util;

import java.util.regex.Pattern;

public class CustomerOrdersValidator {
    private static final String INVALID_ORDER = "유효하지 않은 주문입니다.";
    private static final Pattern ORDER_MENU_PATTERN = Pattern.compile("([가-힣]+-[0-9]+)(,[가-힣]+-[0-9]+)*");

    private CustomerOrdersValidator() {
    }

    public static void validate(String input) {
        if (!matchesPattern(input, ORDER_MENU_PATTERN)) {
            throw new IllegalArgumentException("{메뉴 이름}-{수량} 형식으로 입력해 주세요.\n" + INVALID_ORDER);
        }
    }

    private static boolean matchesPattern(String input, Pattern pattern) {
        return pattern.matcher(input).matches();
    }
}
