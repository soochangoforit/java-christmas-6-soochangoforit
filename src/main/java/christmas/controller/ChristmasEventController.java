package christmas.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import christmas.dto.request.OrderItemInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.dto.response.AppliedDiscountsDto;
import christmas.dto.response.EventBadgeDto;
import christmas.dto.response.OrderAmountsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.TotalDiscountAmountsDto;
import christmas.dto.response.VisitDateDto;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountEventManager;
import christmas.model.EventBadge;
import christmas.model.EventSchedule;
import christmas.model.Order;
import christmas.model.OrderAmounts;
import christmas.model.OrderInfo;
import christmas.model.OrderItemMapper;
import christmas.model.PromotionItem;
import christmas.model.TotalDiscountAmounts;
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
        AppliedDiscountEventResult appliedDiscountEventResult = applyDiscountEvents(orderInfo);
        printDiscountEventPreviewMessage(visitDate);
        printCustomerOrder(orderInfo);

        OrderAmounts orderAmounts = orderInfo.calculateOrderAmounts();
        TotalDiscountAmounts totalDiscountedAmounts = appliedDiscountEventResult.calculateTotalDiscountAmounts();

        OrderAmountsDto orderAmountsDto = OrderAmountsDto.from(orderAmounts);
        outputView.printOrderAmountsBeforeDiscount(orderAmountsDto);

        printPromotionMessage(orderInfo);
        printAppliedDiscounts(appliedDiscountEventResult);

        TotalDiscountAmountsDto totalDiscountAmountsDto = TotalDiscountAmountsDto.from(totalDiscountedAmounts);
        outputView.printTotalDiscountAmounts(totalDiscountAmountsDto);

        OrderAmounts orderAmountsAfterDiscount = orderAmounts.subtract(totalDiscountedAmounts);
        OrderAmountsDto orderAmountsAfterDiscountDto = OrderAmountsDto.from(orderAmountsAfterDiscount);
        outputView.printOrderAmountsAfterDiscount(orderAmountsAfterDiscountDto);

        EventBadge eventBadge = EventBadge.findMatchingEventBadge(totalDiscountedAmounts);
        EventBadgeDto eventBadgeDto = EventBadgeDto.from(eventBadge);
        outputView.printEventBadge(eventBadgeDto);
    }

    private AppliedDiscountEventResult applyDiscountEvents(OrderInfo orderInfo) {
        return discountEventManager.applyDiscountEvents(orderInfo);
    }

    private void printWelcomeMessage() {
        outputView.printWelcomeMessage(EventSchedule.MAIN_EVENT_SEASON.getMonth());
    }

    private VisitDate fetchVisitDateFromCustomer() {
        return retryOnException(this::createVisitDate);
    }

    private VisitDate createVisitDate() {
        VisitDayDto visitDayDto = retryOnException(this::readVisitDay);
        int visitDay = visitDayDto.getDay();
        return VisitDate.from(visitDay);
    }

    private VisitDayDto readVisitDay() {
        return inputView.readVisitDay();
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

    private OrderInfo createOrderInfo(Order order, VisitDate visitDate) {
        return OrderInfo.of(order, visitDate);
    }

    private void printAppliedDiscounts(AppliedDiscountEventResult appliedDiscountEventResult) {
        AppliedDiscountsDto appliedDiscountsDto = AppliedDiscountsDto.from(appliedDiscountEventResult);
        outputView.printAppliedDiscounts(appliedDiscountsDto);
    }

    // TODO : Optional 말고 다른 해결책?
    private void printPromotionMessage(OrderInfo orderInfo) {
        OrderAmounts orderAmounts = orderInfo.calculateOrderAmounts();
        Optional<PromotionItem> matchingPromotion = PromotionItem.findMatchingPromotion(orderAmounts);
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

    private List<OrderItemMapper> convertFrom(List<OrderItemInfoDto> orderItemInfoDtos) {
        List<OrderItemMapper> orderItemMappers = orderItemInfoDtos.stream()
                .map(OrderItemMapper::from)
                .toList();

        return orderItemMappers;
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
