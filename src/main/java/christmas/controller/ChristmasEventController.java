package christmas.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import christmas.dto.request.OrderItemInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.dto.response.AppliedDiscountsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.VisitDateDto;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountEventManager;
import christmas.model.EventBadge;
import christmas.model.Order;
import christmas.model.OrderInfo;
import christmas.model.OrderItemMapper;
import christmas.model.PromotionItem;
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
        outputView.printWelcomeMessage();
        VisitDate visitDate = retryOnException(this::createDateOfVisit);
        Order order = retryOnException(this::createOrder);
        OrderInfo orderInfo = OrderInfo.of(order, visitDate);
        AppliedDiscountEventResult appliedDiscountEventResult = discountEventManager.applyDiscountEvents(orderInfo);
        printDiscountEventPreviewMessage(visitDate);
        printCustomerOrder(orderInfo);

        int totalPrice = orderInfo.calculateTotalPrice();
        int totalDiscountedAmount = appliedDiscountEventResult.calculateTotalDiscountedAmount();

        outputView.printTotalPriceBeforeDiscount(totalPrice);
        printPromotionMessage(orderInfo);
        printAppliedDiscounts(appliedDiscountEventResult);
        outputView.printTotalDiscountedAmount(totalDiscountedAmount);
        outputView.printTotalPriceAfterDiscount(totalPrice, totalDiscountedAmount);
        EventBadge eventBadge = EventBadge.findMatchingEventBadge(totalDiscountedAmount);
        outputView.printEventBadge(eventBadge);

    }

    private void printAppliedDiscounts(AppliedDiscountEventResult appliedDiscountEventResult) {
        AppliedDiscountsDto appliedDiscountsDto = AppliedDiscountsDto.from(appliedDiscountEventResult);
        outputView.printAppliedDiscounts(appliedDiscountsDto);
    }

    private void printPromotionMessage(OrderInfo orderInfo) {
        int totalPrice = orderInfo.calculateTotalPrice();
        Optional<PromotionItem> matchingPromotion = PromotionItem.findMatchingPromotion(totalPrice);
        outputView.printPromotionMessage(matchingPromotion);
    }

    private void printDiscountEventPreviewMessage(VisitDate visitDate) {
        VisitDateDto visitDateDto = VisitDateDto.from(visitDate);
        outputView.printDiscountEventPreviewMessage(visitDateDto);
    }

    private void printCustomerOrder(OrderInfo orderInfo) {
        OrderDto orderDto = OrderDto.from(orderInfo);
        outputView.printCustomerOrder(orderDto);
    }

    private Order createOrder() {
        List<OrderItemInfoDto> orderItemInfoDtos = retryOnException(this::readCustomerOrder);
        List<OrderItemMapper> orderItemMappers = convertFrom(orderItemInfoDtos);

        return Order.from(orderItemMappers);
    }

    private List<OrderItemMapper> convertFrom(List<OrderItemInfoDto> orderItemInfoDtos) {
        List<OrderItemMapper> orderItemMappers = orderItemInfoDtos.stream()
                .map(OrderItemMapper::from)
                .toList();

        return orderItemMappers;
    }

    private List<OrderItemInfoDto> readCustomerOrder() {
        return inputView.readCustomerOrder();
    }

    private VisitDate createDateOfVisit() {
        VisitDayDto visitDayDto = retryOnException(this::readVisitDay);
        int visitDay = visitDayDto.getDay();
        return VisitDate.from(visitDay);
    }

    private VisitDayDto readVisitDay() {
        return inputView.readVisitDay();
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            return retryOnException(supplier);
        }
    }

    private <T, R> R retryOnException(Function<T, R> function, T input) {
        try {
            return function.apply(input);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            return retryOnException(function, input);
        }
    }
}
