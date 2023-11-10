package christmas.view;

import java.util.Optional;
import christmas.dto.response.DateOfVisitInfoDto;
import christmas.dto.response.OrderInfoDto;
import christmas.dto.response.OrderResultDto;
import christmas.model.Menu;
import christmas.model.PromotionItem;

public class OutputView {
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";

    public void printExceptionMessage(String message) {
        String exceptionMessage = String.format(EXCEPTION_FORMAT, message);
        System.out.println(exceptionMessage);
    }

    public void printWelcomeMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    public void printDiscountPreviewMessage(DateOfVisitInfoDto dateOfVisitInfoDto) {
        int day = dateOfVisitInfoDto.getDay();
        int month = dateOfVisitInfoDto.getMonth();

        System.out.println(String.format("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", month, day));
        System.out.println();
    }

    public void printOrderResult(OrderResultDto orderResultDto) {
        System.out.println("<주문 메뉴>");
        orderResultDto.getMenuInfoDtos().forEach(this::printOrderInfo);
        System.out.println();
    }

    private void printOrderInfo(OrderInfoDto orderInfoDto) {
        String menuName = orderInfoDto.getMenuName();
        int quantity = orderInfoDto.getQuantity();

        System.out.println(String.format("%s %d개", menuName, quantity));
    }

    public void printPromotionMessage(Optional<PromotionItem> matchingPromotion) {
        System.out.println("<증정 메뉴>");
        if (matchingPromotion.isPresent()) {
            PromotionItem promotionItem = matchingPromotion.get();
            Menu item = promotionItem.getItem();

            String promotionItemName = item.getName();
            int promotionItemQuantity = promotionItem.getQuantity();

            System.out.println(String.format("%s %d개", promotionItemName, promotionItemQuantity));
            return;
        }
        System.out.println("없음");
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(String.format("%,d원", totalPrice));
        System.out.println();
    }

}
