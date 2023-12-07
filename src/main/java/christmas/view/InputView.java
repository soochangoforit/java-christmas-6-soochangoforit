package christmas.view;

import java.util.List;
import camp.nextstep.edu.missionutils.Console;
import christmas.dto.request.OrderItemDto;
import christmas.view.validator.BlankValidator;
import christmas.view.validator.DigitsOnlyValidator;
import christmas.view.validator.OrderValidator;

public class InputView {
    public int readVisitDay() {
        println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String visitDay = readLine();
        validateVisitDay(visitDay);
        return convertToInt(visitDay);
    }

    private void println(String message) {
        System.out.println(message);
    }

    private String readLine() {
        return Console.readLine().trim();
    }

    private void validateVisitDay(String visitDay) {
        BlankValidator.validate(visitDay, "공백 문자는 입력할 수 없습니다.");
        DigitsOnlyValidator.validate(visitDay, "유효하지 않은 날짜입니다.");
    }

    private int convertToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자로 변환할 수 없는 문자입니다.");
        }
    }

    public List<OrderItemDto> readOrder() {
        println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String rawOrder = readLine();
        validateOrder(rawOrder);
        List<String> rawOrderItems = split(",", rawOrder);
        return rawOrderItems.stream()
                .map(this::convertToOrderItem)
                .toList();
    }

    private void validateOrder(String rawOrder) {
        BlankValidator.validate(rawOrder, "공백 문자는 입력할 수 없습니다.");
        OrderValidator.validate(rawOrder, "유효하지 않은 주문입니다.");
    }

    private List<String> split(String format, String input) {
        return List.of(input.split(format));
    }

    private OrderItemDto convertToOrderItem(String rawOrderItem) {
        List<String> rawOrderItemInfo = split("-", rawOrderItem);
        String name = rawOrderItemInfo.get(0);
        int count = convertToInt(rawOrderItemInfo.get(1));
        return new OrderItemDto(name, count);
    }

}
