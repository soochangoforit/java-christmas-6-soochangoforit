package christmas.view;

import java.util.Map;
import java.util.Optional;
import christmas.dto.response.AppliedDiscountsDto;
import christmas.dto.response.OrderInfoDto;
import christmas.dto.response.OrderResultDto;
import christmas.dto.response.VisitDateDto;
import christmas.model.DiscountEventType;
import christmas.model.EventBadge;
import christmas.model.Menu;
import christmas.model.PromotionItem;

public class OutputView {
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";
    private static final String EVENT_PREVIEW_MESSAGE_FORMAT = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";

    public void printExceptionMessage(String message) {
        String exceptionMessage = String.format(EXCEPTION_FORMAT, message);
        print(exceptionMessage);
    }

    private void print(String message) {
        System.out.println(message);
    }

    public void printWelcomeMessage() {
        print("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    public void printDiscountEventPreviewMessage(VisitDateDto visitDateDto) {
        int month = visitDateDto.getMonth();
        int day = visitDateDto.getDay();

        String eventPreviewMessage = formatEventPreviewMessage(month, day);
        print(eventPreviewMessage);
        printEmptyLine();
    }

    private void printEmptyLine() {
        System.out.println();
    }

    private static String formatEventPreviewMessage(int month, int day) {
        return String.format(EVENT_PREVIEW_MESSAGE_FORMAT, month, day);
    }

    public void printOrderResult(OrderResultDto orderResultDto) {
        print("<주문 메뉴>");
        orderResultDto.getMenuInfoDtos().forEach(this::printOrderInfo);
        printEmptyLine();
    }

    private void printOrderInfo(OrderInfoDto orderInfoDto) {
        String menuName = orderInfoDto.getMenuName();
        int quantity = orderInfoDto.getQuantity();

        print(String.format("%s %d개", menuName, quantity));
    }

    public void printEventBadge(EventBadge eventBadge) {
        print("<12월 이벤트 배지>");
        print(eventBadge.getName());
    }

    public void printTotalPriceAfterDiscount(int totalPrice, int totalDiscountedAmount) {
        print("<할인 후 예상 결제 금액>");
        print(String.format("%,d원", totalPrice - totalDiscountedAmount));
        printEmptyLine();
    }

    public void printTotalDiscountedAmount(int totalDiscountedAmount) {
        print("<총혜택 금액>");
        String discountAmountMessage = String.format("-%,d원", totalDiscountedAmount);
        if (totalDiscountedAmount == 0) {
            discountAmountMessage = "0원";
        }
        print(discountAmountMessage);
        printEmptyLine();
    }

    public void printAppliedDiscounts(AppliedDiscountsDto appliedDiscountsDto) {
        Map<DiscountEventType, Integer> appliedDiscounts = appliedDiscountsDto.getAppliedDiscounts();

        print("<혜택 내역>");
        if (appliedDiscounts.isEmpty()) {
            print("없음");
            printEmptyLine();
            return;
        }
        appliedDiscounts.forEach(this::printAppliedDiscount);
        printEmptyLine();
    }

    private void printAppliedDiscount(DiscountEventType discountEventType, Integer integer) {
        String discountName = discountEventType.getName();
        int discountAmount = integer;

        print(String.format("%s: -%,d원", discountName, discountAmount));
    }

    public void printPromotionMessage(Optional<PromotionItem> matchingPromotion) {
        print("<증정 메뉴>");
        if (matchingPromotion.isPresent()) {
            PromotionItem promotionItem = matchingPromotion.get();
            Menu item = promotionItem.getItem();

            String promotionItemName = item.getName();
            int promotionItemQuantity = promotionItem.getQuantity();

            print(String.format("%s %d개", promotionItemName, promotionItemQuantity));
            printEmptyLine();
            return;
        }
        print("없음");
        printEmptyLine();
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        print("<할인 전 총주문 금액>");
        print(String.format("%,d원", totalPrice));
        printEmptyLine();
    }

}
