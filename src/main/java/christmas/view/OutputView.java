package christmas.view;

import java.util.List;
import java.util.Map;
import christmas.dto.response.AppliedDiscountEventResultDto;
import christmas.dto.response.EventBadgeDto;
import christmas.dto.response.OrderAmountsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.OrderItemDto;
import christmas.dto.response.PromotionItemDto;
import christmas.dto.response.TotalBenefitAmountsDto;
import christmas.dto.response.VisitDateDto;

public class OutputView {
    public static final String WELCOME_MESSAGE_FORMAT = "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.";
    public static final String ORDER_MENU_START_MESSAGE = "<주문 메뉴>";
    public static final String NAME_WITH_QUANTITY_FORMAT = "%s %d개";
    public static final String EVENT_BADGE_MESSAGE = "<%d월 이벤트 배지>";
    private static final String EXCEPTION_FORMAT = "%s 다시 입력해 주세요.";
    private static final String EVENT_PREVIEW_MESSAGE = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_AMOUNTS_BEFORE_DISCOUNT_MESSAGE = "<할인 전 총주문 금액>";
    private static final String POSITIVE_AMOUNTS_FORMAT = "%,d원";
    private static final String PROMOTION_RESULT_MESSAGE = "<증정 메뉴>";
    private static final String NOTHING_MESSAGE = "없음";
    private static final int ZERO_VALUE = 0;
    private static final String APPLIED_DISCOUNTS_RESULT_MESSAGE = "<혜택 내역>";
    private static final String APPLIED_DISCOUNT_FORMAT = "%s: %s";
    private static final String TOTAL_BENEFIT_AMOUNTS_MESSAGE = "<총혜택 금액>";
    private static final String NEGATIVE_AMOUNTS_FORMAT = "-%,d원";
    private static final String ORDER_AMOUNTS_AFTER_DISCOUNT_MESSAGE = "<할인 후 예상 결제 금액>";

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
        return String.format(EVENT_PREVIEW_MESSAGE, month, day);
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

        String orderItemMessage = formatItemWithQuantity(menuName, quantity);
        print(orderItemMessage);
    }

    private String formatItemWithQuantity(String name, int quantity) {
        return String.format(NAME_WITH_QUANTITY_FORMAT, name, quantity);
    }

    public void printOrderAmountsBeforeDiscount(OrderAmountsDto orderAmountsDto) {
        int orderAmounts = orderAmountsDto.getAmounts();
        print(ORDER_AMOUNTS_BEFORE_DISCOUNT_MESSAGE);
        String orderAmountsMessage = formatPositiveAmounts(orderAmounts);
        print(orderAmountsMessage);
        printEmptyLine();
    }

    private String formatPositiveAmounts(int totalDiscountedAmount) {
        return String.format(POSITIVE_AMOUNTS_FORMAT, totalDiscountedAmount);
    }

    public void printPromotionMessage(PromotionItemDto promotionItemDto) {
        String itemName = promotionItemDto.getName();
        int itemQuantity = promotionItemDto.getQuantity();

        print(PROMOTION_RESULT_MESSAGE);
        String promotionResultMessage = formatPromotionResult(itemName, itemQuantity);
        print(promotionResultMessage);
        printEmptyLine();
    }

    private String formatPromotionResult(String itemName, int itemQuantity) {
        if (itemQuantity == ZERO_VALUE) {
            return NOTHING_MESSAGE;
        }
        return formatItemWithQuantity(itemName, itemQuantity);
    }

    public void printAppliedDiscountEventResult(AppliedDiscountEventResultDto appliedDiscountEventResultDto) {
        Map<String, Integer> appliedDiscountEventResult = appliedDiscountEventResultDto.getDiscountEventResult();

        print(APPLIED_DISCOUNTS_RESULT_MESSAGE);
        if (appliedDiscountEventResult.isEmpty()) {
            print(NOTHING_MESSAGE);
            printEmptyLine();
            return;
        }

        appliedDiscountEventResult.forEach(this::printAppliedDiscount);
        printEmptyLine();
    }

    private void printAppliedDiscount(String discountEventName, int discountedAmount) {
        String negativeDiscountAmounts = formatNegativeAmounts(discountedAmount);
        String appliedDiscount = String.format(APPLIED_DISCOUNT_FORMAT, discountEventName, negativeDiscountAmounts);
        print(appliedDiscount);
    }

    private String formatNegativeAmounts(int amounts) {
        return String.format(NEGATIVE_AMOUNTS_FORMAT, amounts);
    }

    public void printTotalBenefitAmounts(TotalBenefitAmountsDto totalBenefitAmountsDto) {
        int totalBenefitAmounts = totalBenefitAmountsDto.getTotalBenefitAmounts();
        print(TOTAL_BENEFIT_AMOUNTS_MESSAGE);
        String benefitAmountsMessage = formatTotalBenefitAmountsMessage(totalBenefitAmounts);
        print(benefitAmountsMessage);
        printEmptyLine();
    }

    private String formatTotalBenefitAmountsMessage(int totalBenefitAmounts) {
        if (totalBenefitAmounts == ZERO_VALUE) {
            return formatPositiveAmounts(totalBenefitAmounts);
        }

        return formatNegativeAmounts(totalBenefitAmounts);
    }

    public void printOrderAmountsAfterDiscount(OrderAmountsDto orderAmountsAfterDiscountDto) {
        int orderAmountsAfterDiscount = orderAmountsAfterDiscountDto.getAmounts();
        print(ORDER_AMOUNTS_AFTER_DISCOUNT_MESSAGE);
        String orderAmountsAfterDiscountMessage = formatPositiveAmounts(orderAmountsAfterDiscount);
        print(orderAmountsAfterDiscountMessage);
        printEmptyLine();
    }

    public void printEventBadge(EventBadgeDto eventBadgeDto) {
        int eventMonth = eventBadgeDto.getEventMonth();
        String eventBadgeName = eventBadgeDto.getBadgeName();

        String eventBadgeMessage = String.format(EVENT_BADGE_MESSAGE, eventMonth);
        print(eventBadgeMessage);
        print(eventBadgeName);
    }
}
