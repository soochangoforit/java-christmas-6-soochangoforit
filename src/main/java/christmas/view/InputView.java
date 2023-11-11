package christmas.view;

import java.util.List;
import java.util.stream.Stream;
import camp.nextstep.edu.missionutils.Console;
import christmas.dto.request.OrderInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.util.BlankValidator;
import christmas.util.CustomerOrdersValidator;
import christmas.util.DigitsOnlyValidator;

public class InputView {
    private static final String VISIT_DAY_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String NUMBER_FORMAT_EXCEPTION = "숫자 형식의 문자만 입력할 수 있습니다.";
    private static final String CUSTOMER_ORDERS_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. "
            + "(e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDER_DELIMITER = ",";
    private static final String ORDER_INFO_DELIMITER = "-";
    private static final int ZERO_INDEX = 0;
    private static final int FIRST_INDEX = 1;

    public VisitDayDto readVisitDay() {
        System.out.println(VISIT_DAY_MESSAGE);
        String rawVisitDay = Console.readLine();
        validateVisitDay(rawVisitDay);
        int visitDay = convertToInt(rawVisitDay);
        return new VisitDayDto(visitDay);
    }

    private void validateVisitDay(String rawVisitDay) {
        BlankValidator.validate(rawVisitDay);
        DigitsOnlyValidator.validate(rawVisitDay);
    }

    private int convertToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMBER_FORMAT_EXCEPTION);
        }
    }

    public List<OrderInfoDto> readCustomerOrders() {
        System.out.println(CUSTOMER_ORDERS_MESSAGE);
        String rawCustomerOrders = Console.readLine();
        validateCustomerOrders(rawCustomerOrders);
        return convertFrom(rawCustomerOrders);
    }

    private void validateCustomerOrders(String rawCustomerOrders) {
        BlankValidator.validate(rawCustomerOrders);
        CustomerOrdersValidator.validate(rawCustomerOrders);
    }

    private List<OrderInfoDto> convertFrom(String rawCustomerOrders) {
        return Stream.of(rawCustomerOrders.split(ORDER_DELIMITER))
                .map(this::toOrderInfoDto)
                .toList();
    }

    private OrderInfoDto toOrderInfoDto(String order) {
        String[] orderInfo = order.split(ORDER_INFO_DELIMITER);
        String menuName = orderInfo[ZERO_INDEX];
        int quantity = Integer.parseInt(orderInfo[FIRST_INDEX]);

        return new OrderInfoDto(menuName, quantity);
    }

}
