package christmas.view.validator;

import java.util.regex.Pattern;

public class OrderValidator {
    private static final Pattern ORDER_PATTERN = Pattern.compile("([가-힣]+-[0-9]+)(,[가-힣]+-[0-9]+)*");

    private OrderValidator() {
    }

    public static void validate(String input, String exceptionMessage) {
        if (!matchesPattern(input, ORDER_PATTERN)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private static boolean matchesPattern(String input, Pattern pattern) {
        return pattern.matcher(input).matches();
    }
}
