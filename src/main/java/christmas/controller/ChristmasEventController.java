package christmas.controller;

import java.util.List;
import java.util.function.Supplier;
import christmas.dto.request.OrderItemInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.dto.response.AppliedDiscountEventResultDto;
import christmas.dto.response.EventBadgeDto;
import christmas.dto.response.OrderAmountsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.PromotionItemDto;
import christmas.dto.response.TotalBenefitAmountsDto;
import christmas.dto.response.VisitDateDto;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountAmounts;
import christmas.model.DiscountEventManager;
import christmas.model.EventBadge;
import christmas.model.EventSchedule;
import christmas.model.Order;
import christmas.model.OrderAmounts;
import christmas.model.OrderInfo;
import christmas.model.OrderItemMapper;
import christmas.model.PromotionItem;
import christmas.model.TotalBenefitAmounts;
import christmas.model.VisitDate;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ChristmasEventController {
    private final InputView inputView;
    private final OutputView outputView;
    private final DiscountEventManager discountEventManager;

    public ChristmasEventController(InputView inputView, OutputView outputView,
                                    DiscountEventManager discountEventManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.discountEventManager = discountEventManager;
    }

    public void run() {
        printWelcomeMessage();
        VisitDate visitDate = fetchVisitDateFromCustomer();
        Order order = fetchOrderFromCustomer();
        OrderInfo orderInfo = createOrderInfo(order, visitDate);
        OrderAmounts orderAmountsBeforeDiscount = orderInfo.calculateOrderAmounts();
        printOrderDetails(orderInfo, orderAmountsBeforeDiscount);

        AppliedDiscountEventResult appliedDiscountResult = applyDiscountEvents(orderInfo);
        TotalBenefitAmounts totalBenefitAmounts = appliedDiscountResult.calculateTotalBenefitAmounts();
        DiscountAmounts totalDiscountAmounts = appliedDiscountResult.calculateTotalDiscountAmounts();
        printAppliedDiscounts(appliedDiscountResult);
        printTotalBenefitAmounts(totalBenefitAmounts);
        printOrderAmountsAfterDiscount(orderAmountsBeforeDiscount, totalDiscountAmounts);
        printEventBadge(totalBenefitAmounts);
    }

    private void printWelcomeMessage() {
        outputView.printWelcomeMessage(EventSchedule.MAIN_EVENT_SEASON.getMonth());
    }

    private VisitDate fetchVisitDateFromCustomer() {
        return retryOnException(this::createVisitDate);
    }

    private VisitDate createVisitDate() {
        VisitDayDto visitDayDto = retryOnException(this::readVisitDay);
        int eventYear = EventSchedule.MAIN_EVENT_SEASON.getYear();
        int eventMonth = EventSchedule.MAIN_EVENT_SEASON.getMonth();
        int visitDay = visitDayDto.getDay();
        return VisitDate.from(eventYear, eventMonth, visitDay);
    }

    private VisitDayDto readVisitDay() {
        return inputView.readVisitDay(EventSchedule.MAIN_EVENT_SEASON.getMonth());
    }

    private Order fetchOrderFromCustomer() {
        return retryOnException(this::createOrder);
    }

    private Order createOrder() {
        List<OrderItemInfoDto> orderItemInfoDtos = retryOnException(this::readCustomerOrder);
        List<OrderItemMapper> orderItemMappers = convertFrom(orderItemInfoDtos);

        return Order.from(orderItemMappers);
    }

    private List<OrderItemInfoDto> readCustomerOrder() {
        return inputView.readCustomerOrder();
    }

    private List<OrderItemMapper> convertFrom(List<OrderItemInfoDto> orderItemInfoDtos) {
        return orderItemInfoDtos.stream()
                .map(OrderItemMapper::from)
                .toList();
    }

    private OrderInfo createOrderInfo(Order order, VisitDate visitDate) {
        return OrderInfo.of(order, visitDate);
    }

    private void printOrderDetails(OrderInfo orderInfo, OrderAmounts orderAmountsBeforeDiscount) {
        printDiscountEventPreviewMessage(orderInfo.getVisitDate());
        printCustomerOrder(orderInfo);
        printOrderAmountsBeforeDiscount(orderAmountsBeforeDiscount);
        printPromotionMessage(orderAmountsBeforeDiscount);
    }

    private void printDiscountEventPreviewMessage(VisitDate visitDate) {
        VisitDateDto visitDateDto = VisitDateDto.from(visitDate);
        outputView.printDiscountEventPreviewMessage(visitDateDto);
    }

    private void printCustomerOrder(OrderInfo orderInfo) {
        OrderDto orderDto = OrderDto.from(orderInfo);
        outputView.printCustomerOrder(orderDto);
    }

    private void printOrderAmountsBeforeDiscount(OrderAmounts orderAmounts) {
        OrderAmountsDto orderAmountsDto = OrderAmountsDto.from(orderAmounts);
        outputView.printOrderAmountsBeforeDiscount(orderAmountsDto);
    }

    private void printPromotionMessage(OrderAmounts orderAmountsBeforeDiscount) {
        PromotionItem matchingPromotionItem = PromotionItem.findMatchingPromotion(orderAmountsBeforeDiscount);
        PromotionItemDto promotionItemDto = PromotionItemDto.from(matchingPromotionItem);
        outputView.printPromotionMessage(promotionItemDto);
    }

    private AppliedDiscountEventResult applyDiscountEvents(OrderInfo orderInfo) {
        return discountEventManager.applyDiscountEvents(orderInfo);
    }

    private void printAppliedDiscounts(AppliedDiscountEventResult appliedDiscountEventResult) {
        AppliedDiscountEventResultDto appliedDiscountEventResultDto = AppliedDiscountEventResultDto.from(
                appliedDiscountEventResult);
        outputView.printAppliedDiscountEventResult(appliedDiscountEventResultDto);
    }

    private void printTotalBenefitAmounts(TotalBenefitAmounts totalBenefitAmounts) {
        TotalBenefitAmountsDto totalBenefitAmountsDto = TotalBenefitAmountsDto.from(totalBenefitAmounts);
        outputView.printTotalBenefitAmounts(totalBenefitAmountsDto);
    }

    private void printOrderAmountsAfterDiscount(OrderAmounts orderAmountsBeforeDiscount,
                                                DiscountAmounts totalDiscountAmounts) {
        OrderAmounts orderAmountsAfterDiscount = orderAmountsBeforeDiscount.deductTotalDiscountAmounts(
                totalDiscountAmounts);
        OrderAmountsDto orderAmountsAfterDiscountDto = OrderAmountsDto.from(orderAmountsAfterDiscount);
        outputView.printOrderAmountsAfterDiscount(orderAmountsAfterDiscountDto);
    }

    private void printEventBadge(TotalBenefitAmounts totalDiscountedAmounts) {
        EventBadge eventBadge = EventBadge.findMatchingEventBadge(totalDiscountedAmounts);
        EventBadgeDto eventBadgeDto = EventBadgeDto.from(EventSchedule.MAIN_EVENT_SEASON.getMonth(), eventBadge);
        outputView.printEventBadge(eventBadgeDto);
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            return retryOnException(supplier);
        }
    }
}
