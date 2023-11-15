package christmas.view;

import java.util.List;
import java.util.stream.Stream;
import camp.nextstep.edu.missionutils.Console;
import christmas.dto.request.OrderItemInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.util.BlankValidator;
import christmas.util.CustomerOrderValidator;
import christmas.util.VisitDayValidator;

public class InputView {
    private static final String VISIT_DAY_MESSAGE = "%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String NUMBER_FORMAT_EXCEPTION = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String CUSTOMER_ORDERS_MESSAGE =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDER_ITEM_SEPARATOR = ",";
    private static final String MENU_QUANTITY_DELIMITER = "-";
    private static final int ZERO_INDEX = 0;
    private static final int FIRST_INDEX = 1;

    public VisitDayDto readVisitDay(int eventMonth) {
        String visitDayMessage = formatVisitDayMessage(eventMonth);
        print(visitDayMessage);
        String rawVisitDay = Console.readLine();
        validateVisitDay(rawVisitDay);
        int visitDay = convertToInt(rawVisitDay);
        return new VisitDayDto(visitDay);
    }

    private String formatVisitDayMessage(int eventMonth) {
        return String.format(VISIT_DAY_MESSAGE, eventMonth);
    }

    private void print(String message) {
        System.out.println(message);
    }

    private void validateVisitDay(String rawVisitDay) {
        BlankValidator.validate(rawVisitDay);
        VisitDayValidator.validate(rawVisitDay);
    }

    private int convertToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMBER_FORMAT_EXCEPTION);
        }
    }

    public List<OrderItemInfoDto> readCustomerOrder() {
        print(CUSTOMER_ORDERS_MESSAGE);
        String rawCustomerOrder = Console.readLine();
        validateCustomerOrder(rawCustomerOrder);
        return convertFrom(rawCustomerOrder);
    }

    private void validateCustomerOrder(String rawCustomerOrder) {
        BlankValidator.validate(rawCustomerOrder);
        CustomerOrderValidator.validate(rawCustomerOrder);
    }

    private List<OrderItemInfoDto> convertFrom(String rawCustomerOrder) {
        return Stream.of(rawCustomerOrder.split(ORDER_ITEM_SEPARATOR))
                .map(this::toOrderItemInfoDto)
                .toList();
    }

    private OrderItemInfoDto toOrderItemInfoDto(String orderItem) {
        String[] orderItemInfoValues = orderItem.split(MENU_QUANTITY_DELIMITER);
        String menuName = orderItemInfoValues[ZERO_INDEX];
        int quantity = Integer.parseInt(orderItemInfoValues[FIRST_INDEX]);

        return new OrderItemInfoDto(menuName, quantity);
    }

}
