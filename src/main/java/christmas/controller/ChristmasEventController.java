package christmas.controller;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import christmas.dto.request.DateOfVisitDto;
import christmas.dto.request.OrderInfoDto;
import christmas.dto.response.DateOfVisitInfoDto;
import christmas.model.DateOfVisit;
import christmas.model.Discountable;
import christmas.model.OrderGroup;
import christmas.model.OrderResult;
import christmas.model.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ChristmasEventController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;
    private final List<Discountable> discountPolicies;

    public ChristmasEventController(InputView inputView, OutputView outputView, OrderService orderService,
                                    List<Discountable> discountPolicies) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
        this.discountPolicies = discountPolicies;
    }

    public void run() {
        outputView.printWelcomeMessage();
        DateOfVisit dateOfVisit = retryOnException(this::createDateOfVisit);
        OrderGroup orderGroup = retryOnException(this::createOrderGroup, dateOfVisit);
        OrderResult orderResult = OrderResult.from(orderGroup, dateOfVisit);
        printDiscountPreviewMessage(dateOfVisit);
    }

    private void printDiscountPreviewMessage(DateOfVisit dateOfVisit) {
        DateOfVisitInfoDto dateOfVisitInfoDto = DateOfVisitInfoDto.from(dateOfVisit);
        outputView.printDiscountPreviewMessage(dateOfVisitInfoDto);
    }

    private OrderGroup createOrderGroup(DateOfVisit dateOfVisit) {
        List<OrderInfoDto> orderInfoDtos = retryOnException(this::readCustomerOrders);
        return orderService.createOrderGroup(dateOfVisit, orderInfoDtos);
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
