package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.dto.request.DateOfVisitDto;
import christmas.util.BlankValidator;
import christmas.util.DigitsOnlyValidator;

public class InputView {
    private static final String NUMBER_FORMAT_EXCEPTION = "숫자 형식의 문자만 입력할 수 있습니다.";

    public DateOfVisitDto readDateOfVisit() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String rawDateOfVisit = Console.readLine();
        validateDateOfVisit(rawDateOfVisit);
        int dateOfVisit = convertToInt(rawDateOfVisit);
        return new DateOfVisitDto(dateOfVisit);
    }

    private void validateDateOfVisit(String rawDateOfVisit) {
        BlankValidator.validate(rawDateOfVisit);
        DigitsOnlyValidator.validate(rawDateOfVisit);
    }

    private int convertToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMBER_FORMAT_EXCEPTION);
        }
    }
}
