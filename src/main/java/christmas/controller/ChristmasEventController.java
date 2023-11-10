package christmas.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import christmas.dto.request.DateOfVisitDto;
import christmas.dto.request.OrderInfoDto;
import christmas.dto.response.DateOfVisitInfoDto;
import christmas.dto.response.OrderResultDto;
import christmas.model.DateOfVisit;
import christmas.model.OrderGroup;
import christmas.model.OrderResult;
import christmas.model.OrderService;
import christmas.model.PromotionItem;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ChristmasEventController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public ChristmasEventController(InputView inputView, OutputView outputView, OrderService orderService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
    }

    public void run() {
        outputView.printWelcomeMessage();
        DateOfVisit dateOfVisit = retryOnException(this::createDateOfVisit);
        OrderGroup orderGroup = retryOnException(this::createOrderGroup);
        OrderResult orderResult = OrderResult.of(orderGroup, dateOfVisit);
        printDiscountPreviewMessage(dateOfVisit);
        printCustomerOrders(orderResult);
        printTotalPriceBeforeDiscount(orderResult);
        printPromotionMessage(orderResult);
    }

    private void printPromotionMessage(OrderResult orderResult) {
        int totalPrice = orderResult.calculateTotalPrice();
        Optional<PromotionItem> matchingPromotion = PromotionItem.findMatchingPromotion(totalPrice);
        outputView.printPromotionMessage(matchingPromotion);
    }

    private int printTotalPriceBeforeDiscount(OrderResult orderResult) {
        int totalPrice = orderResult.calculateTotalPrice();
        outputView.printTotalPriceBeforeDiscount(totalPrice);
        return totalPrice;
    }

    private void printDiscountPreviewMessage(DateOfVisit dateOfVisit) {
        DateOfVisitInfoDto dateOfVisitInfoDto = DateOfVisitInfoDto.from(dateOfVisit);
        outputView.printDiscountPreviewMessage(dateOfVisitInfoDto);
    }

    private void printCustomerOrders(OrderResult orderResult) {
        OrderResultDto orderResultDto = OrderResultDto.from(orderResult);
        outputView.printOrderResult(orderResultDto);
    }

    private OrderGroup createOrderGroup() {
        List<OrderInfoDto> orderInfoDtos = retryOnException(this::readCustomerOrders);
        return orderService.createOrderGroup(orderInfoDtos);
    }

    private List<OrderInfoDto> readCustomerOrders() {
        return inputView.readCustomerOrders();
    }

    private DateOfVisit createDateOfVisit() {
        DateOfVisitDto dateOfVisitDto = retryOnException(this::readDateOfVisit);
        int date = dateOfVisitDto.getDate();
        return DateOfVisit.from(date);
    }

    private DateOfVisitDto readDateOfVisit() {
        return inputView.readDateOfVisit();
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
