package christmas.view.validator;

import java.util.regex.Pattern;

public class OrderValidator {
    private static final String INVALID_ORDER = "입력 형식을 다시 확인해주세요";
    private static final Pattern ORDER_PATTERN = Pattern.compile("([가-힣]+-[0-9]+)(,[가-힣]+-[0-9]+)*");

    private OrderValidator() {
    }

    public static void validate(String input) {
        if (!matchesPattern(input, ORDER_PATTERN)) {
            throw new IllegalArgumentException(INVALID_ORDER);
        }
    }

    private static boolean matchesPattern(String input, Pattern pattern) {
        return pattern.matcher(input).matches();
    }
}
