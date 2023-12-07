package christmas.view.validator;

public class BlankValidator {

    private BlankValidator() {
    }

    public static void validate(String input, String exceptionMessage) {
        validateBlank(input, exceptionMessage);
    }

    private static void validateBlank(String input, String exceptionMessage) {
        if (isBlank(input)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private static boolean isBlank(String input) {
        return input.isBlank();
    }
}
