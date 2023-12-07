package christmas.view;

import christmas.model.BenefitAmounts;
import christmas.model.BenefitResult;
import christmas.model.EventBadge;
import christmas.model.OrderAmounts;
import christmas.model.OrderInfo;
import christmas.model.PromotionItem;

public class OutputView {
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";

    public void printExceptionMessage(String message) {
        println(String.format(EXCEPTION_FORMAT, message));
    }

    private void println(String message) {
        System.out.println(message);
    }

    public void printEventBadge(EventBadge eventBadge) {
        println("<12월 이벤트 배지>");
        println(eventBadge.getName());
        printEmptyLine();
    }

    private void printEmptyLine() {
        System.out.println();
    }

    public void printOrderAmountsAfterDiscount(OrderAmounts orderAmountsAfterDiscount) {
        println("<할인 후 예상 결제 금액>");
        println(String.format("%,d원", orderAmountsAfterDiscount.getAmounts()));
        printEmptyLine();
    }

    public void printTotalBenefitAmounts(BenefitAmounts totalBenefitAmounts) {
        println("<총혜택 금액>");
        if (totalBenefitAmounts.hasNotAnyBenefit()) {
            println("0원");
            printEmptyLine();
            return;
        }
        println(String.format("-%,d원", totalBenefitAmounts.getAmounts()));
        printEmptyLine();
    }

    public void printBenefitResult(BenefitResult result) {
        println("<혜택 내역>");
        if (result.hasNotAnyBenefit()) {
            println("없음");
            printEmptyLine();
            return;
        }
        result.getResult().forEach((type, amounts) -> {
            if (amounts.hasAnyBenefit()) {
                String formattedBenefit = String.format("%s: %,d원", type.getName(), amounts.getAmounts());
                println(formattedBenefit);
            }
        });
        printEmptyLine();
    }

    public void printPromotionItem(PromotionItem promotionItem) {
        println("<증정 메뉴>");
        if (promotionItem.isNone()) {
            println("없음");
            printEmptyLine();
            return;
        }
        println(String.format("%s %d개", promotionItem.getItem().getName(), promotionItem.getQuantity()));
        printEmptyLine();
    }

    public void printOrderAmounts(OrderAmounts orderAmountsBeforeDiscount) {
        println("<할인 전 총주문 금액>");
        println(String.format("%,d원", orderAmountsBeforeDiscount.getAmounts()));
        printEmptyLine();
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
