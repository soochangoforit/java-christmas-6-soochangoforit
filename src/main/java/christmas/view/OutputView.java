package christmas.view;

import christmas.model.OrderInfo;

public class OutputView {
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";

    public void printExceptionMessage(String message) {
        println(String.format(EXCEPTION_FORMAT, message));
    }

    private void println(String message) {
        System.out.println(message);
    }

    public void printOrder(OrderInfo orderInfo) {
        println("<주문 메뉴>");
        orderInfo.getOrder().getOrderItems().forEach(orderItem -> {
            String formattedOrder = String.format("%s %d개", orderItem.getMenu().getName(),
                    orderItem.getQuantity().getValue());
            println(formattedOrder);
        });
        printEmptyLine();
    }

    private void printEmptyLine() {
        System.out.println();
    }

    public void printPreviewMessage(OrderInfo orderInfo) {
        int month = orderInfo.getVisitDate().getDate().getMonthValue();
        int day = orderInfo.getVisitDate().getDate().getDayOfMonth();

        println(String.format("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", month, day));
        printEmptyLine();
    }

    public void printStartMessage() {
        println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }
}
