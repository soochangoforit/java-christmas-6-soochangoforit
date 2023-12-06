package christmas.view;

import java.util.List;
import java.util.stream.Stream;
import camp.nextstep.edu.missionutils.Console;
import christmas.view.validator.BlankValidator;
import christmas.view.validator.DigitsOnlyValidator;

public class InputView {
    public int readVisitDay() {
        println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String visitDay = readLine();
        validateVisitDay(visitDay);
        return convertToInt(visitDay);
    }

    private void validateVisitDay(String visitDay) {
        BlankValidator.validate(visitDay);
        DigitsOnlyValidator.validate(visitDay);
    }

    private String readLine() {
        return Console.readLine().trim();
    }

    private void println(String message) {
        System.out.println(message);
    }

    private int convertToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자로 변환할 수 없는 문자입니다.");
        }
    }

    private void print(String message) {
        System.out.print(message);
    }

    private void printEmptyLine() {
        System.out.println();
    }

    private List<String> split(String format, String input) {
        return List.of(input.split(format));
    }

    private List<Integer> splitToInt(String delimiter, String input) {
        return Stream.of(input.split(delimiter))
                .map(Integer::parseInt)
                .toList();
    }
}
