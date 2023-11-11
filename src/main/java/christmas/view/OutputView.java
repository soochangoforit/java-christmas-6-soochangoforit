package christmas.view;

import java.util.List;
import java.util.Map;
import christmas.dto.response.AppliedDiscountEventResultDto;
import christmas.dto.response.EventBadgeDto;
import christmas.dto.response.OrderAmountsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.OrderItemDto;
import christmas.dto.response.PromotionItemDto;
import christmas.dto.response.TotalDiscountAmountsDto;
import christmas.dto.response.VisitDateDto;

public class OutputView {
    public static final String WELCOME_MESSAGE_FORMAT = "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.";
    public static final String ORDER_MENU_START_MESSAGE = "<주문 메뉴>";
    public static final String ITEM_FORMAT = "%s %d개";
    public static final String ZERO_TOTAL_DISCOUNT_AMOUNTS_MESSAGE = "0원";
    public static final String EVENT_BADGE_MESSAGE_FORMAT = "<%d월 이벤트 배지>";
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";
    private static final String EVENT_PREVIEW_MESSAGE_FORMAT = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_AMOUNTS_BEFORE_DISCOUNT_MESSAGE = "<할인 전 총주문 금액>";
    private static final String ORDER_AMOUNTS_FORMAT = "%,d원";
    private static final String TOTAL_DISCOUNT_AMOUNTS_MESSAGE = "<총혜택 금액>";
    private static final int ZERO_TOTAL_DISCOUNT_AMOUNTS = 0;
    private static final String TOTAL_DISCOUNT_AMOUNTS_FORMAT = "-%,d원";
    private static final String ORDER_AMOUNTS_AFTER_DISCOUNT_MESSAGE = "<할인 후 예상 결제 금액>";
    private static final String PROMOTION_RESULT_MESSAGE_FORMAT = "<증정 메뉴>\n%s";
    private static final int PROMOTION_ZERO_QUANTITY = 0;
    private static final String NOTHING = "없음";
    private static final String APPLIED_DISCOUNTS_RESULT_MESSAGE = "<혜택 내역>";
    private static final String APPLIED_DISCOUNT_FORMAT = "%s: -%,d원";

    public void printExceptionMessage(String message) {
        String exceptionMessage = String.format(EXCEPTION_FORMAT, message);
        print(exceptionMessage);
    }

    private void print(String message) {
        System.out.println(message);
    }

    public void printWelcomeMessage(int eventMonth) {
        String welcomeMessage = formatWelcomeMessage(eventMonth);
        print(welcomeMessage);
    }

    private String formatWelcomeMessage(int eventMonth) {
        return String.format(WELCOME_MESSAGE_FORMAT, eventMonth);
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

    private String formatEventPreviewMessage(int month, int day) {
        return String.format(EVENT_PREVIEW_MESSAGE_FORMAT, month, day);
    }

    public void printCustomerOrder(OrderDto orderDto) {
        print(ORDER_MENU_START_MESSAGE);
        printOrder(orderDto);
        printEmptyLine();
    }

    private void printOrder(OrderDto orderDto) {
        List<OrderItemDto> orderItemDtos = orderDto.getOrderItemDtos();

        orderItemDtos.forEach(this::printOrderItem);
    }

    private void printOrderItem(OrderItemDto orderItemDto) {
        String menuName = orderItemDto.getMenuName();
        int quantity = orderItemDto.getQuantity();

        String orderItemMessage = formatOrderItemMessage(menuName, quantity);
        print(orderItemMessage);
    }

    private String formatOrderItemMessage(String menuName, int quantity) {
        return String.format(ITEM_FORMAT, menuName, quantity);
    }

    public void printEventBadge(EventBadgeDto eventBadgeDto) {
        int eventMonth = eventBadgeDto.getEventMonth();
        String eventBadgeName = eventBadgeDto.getBadgeName();
        String eventBadgeMessage = String.format(EVENT_BADGE_MESSAGE_FORMAT, eventMonth);
        print(eventBadgeMessage);
        print(eventBadgeName);
    }

    public void printOrderAmountsAfterDiscount(OrderAmountsDto orderAmountsAfterDiscountDto) {
        int orderAmountsAfterDiscount = orderAmountsAfterDiscountDto.getAmounts();
        print(ORDER_AMOUNTS_AFTER_DISCOUNT_MESSAGE);
        String orderAmountsAfterDiscountMessage = formatOrderAmountsAfterDiscountMessage(orderAmountsAfterDiscount);
        print(orderAmountsAfterDiscountMessage);
        printEmptyLine();
    }

    private String formatOrderAmountsAfterDiscountMessage(int orderAmountsAfterDiscount) {
        return String.format(ORDER_AMOUNTS_FORMAT, orderAmountsAfterDiscount);
    }

    public void printTotalDiscountAmounts(TotalDiscountAmountsDto totalDiscountAmountsDto) {
        int totalDiscountAmounts = totalDiscountAmountsDto.getTotalDiscountAmounts();
        print(TOTAL_DISCOUNT_AMOUNTS_MESSAGE);
        String discountAmountMessage = formatTotalDiscountAmountsMessage(totalDiscountAmounts);
        print(discountAmountMessage);
        printEmptyLine();
    }

    private String formatTotalDiscountAmountsMessage(int totalDiscountedAmount) {
        if (totalDiscountedAmount == ZERO_TOTAL_DISCOUNT_AMOUNTS) {
            return ZERO_TOTAL_DISCOUNT_AMOUNTS_MESSAGE;
        }

        return String.format(TOTAL_DISCOUNT_AMOUNTS_FORMAT, totalDiscountedAmount);
    }

    public void printAppliedDiscounts(AppliedDiscountEventResultDto appliedDiscountEventResultDto) {
        Map<String, Integer> discountEventResult = appliedDiscountEventResultDto.getDiscountEventResult();

        print(APPLIED_DISCOUNTS_RESULT_MESSAGE);
        if (discountEventResult.isEmpty()) {
            print(NOTHING);
            printEmptyLine();
            return;
        }
        discountEventResult.forEach(this::printAppliedDiscount);
        printEmptyLine();
    }

    private void printAppliedDiscount(String discountEventName, int discountedAmount) {
        print(String.format(APPLIED_DISCOUNT_FORMAT, discountEventName, discountedAmount));
    }

    public void printPromotionMessage(PromotionItemDto promotionItemDto) {
        String itemName = promotionItemDto.getName();
        int itemQuantity = promotionItemDto.getQuantity();

        String promotionResult = formatPromotionResult(itemName, itemQuantity);
        String promotionResultMessage = String.format(PROMOTION_RESULT_MESSAGE_FORMAT, promotionResult);
        print(promotionResultMessage);
        printEmptyLine();
    }

    private String formatPromotionResult(String itemName, int itemQuantity) {
        if (itemQuantity == PROMOTION_ZERO_QUANTITY) {
            return NOTHING;
        }
        return String.format(ITEM_FORMAT, itemName, itemQuantity);
    }

    public void printOrderAmountsBeforeDiscount(OrderAmountsDto orderAmountsDto) {
        int orderAmounts = orderAmountsDto.getAmounts();
        print(ORDER_AMOUNTS_BEFORE_DISCOUNT_MESSAGE);
        String orderAmountsMessage = formatOrderAmountsMessage(orderAmounts);
        print(orderAmountsMessage);
        printEmptyLine();
    }

    private String formatOrderAmountsMessage(int orderAmounts) {
        return String.format(ORDER_AMOUNTS_FORMAT, orderAmounts);
    }

}
