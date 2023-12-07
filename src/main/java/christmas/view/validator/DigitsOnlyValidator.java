package christmas.view.validator;

import java.util.regex.Pattern;

public class DigitsOnlyValidator {
    private static final Pattern DIGITS_ONLY = Pattern.compile("[0-9]+");

    private DigitsOnlyValidator() {
    }

    public static void validate(String input, String exceptionMessage) {
        if (!matchesPattern(input, DIGITS_ONLY)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private static boolean matchesPattern(String input, Pattern pattern) {
        return pattern.matcher(input).matches();
    }
}
