package christmas.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import camp.nextstep.edu.missionutils.Console;
import christmas.dto.request.OrderInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.util.BlankValidator;
import christmas.util.DigitsOnlyValidator;
import christmas.util.OrderValidator;

public class InputView {
    private static final String VISIT_DAY_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String NUMBER_FORMAT_EXCEPTION = "숫자 형식의 문자만 입력할 수 있습니다.";

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
        // 주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String rawCustomerOrders = Console.readLine();
        validateCustomerOrders(rawCustomerOrders);
        return convertToOrderInfoDto(rawCustomerOrders);
    }

    private List<OrderInfoDto> convertToOrderInfoDto(String rawCustomerOrders) {
        return Stream.of(rawCustomerOrders.split(","))
                .map(this::toOrderInfoDto)
                .collect(Collectors.toList());
    }

    private OrderInfoDto toOrderInfoDto(String order) {
        String[] orderInfo = order.split("-");
        String menuName = orderInfo[0];
        int quantity = Integer.parseInt(orderInfo[1]);
        return new OrderInfoDto(menuName, quantity);
    }

    private void validateCustomerOrders(String rawCustomerOrders) {
        BlankValidator.validate(rawCustomerOrders);
        OrderValidator.validate(rawCustomerOrders);
    }
}
