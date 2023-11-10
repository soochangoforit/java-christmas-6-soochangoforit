package christmas.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import christmas.dto.request.DateOfVisitDto;
import christmas.dto.request.OrderInfoDto;
import christmas.dto.response.AppliedDiscountsDto;
import christmas.dto.response.DateOfVisitInfoDto;
import christmas.dto.response.OrderResultDto;
import christmas.model.AppliedDiscounts;
import christmas.model.DateOfVisit;
import christmas.model.DiscountPolicyManager;
import christmas.model.EventBadge;
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
    private final DiscountPolicyManager discountPolicyManager;

    public ChristmasEventController(InputView inputView, OutputView outputView, OrderService orderService,
                                    DiscountPolicyManager discountPolicyManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
        this.discountPolicyManager = discountPolicyManager;
    }

    public void run() {
        outputView.printWelcomeMessage();
        DateOfVisit dateOfVisit = retryOnException(this::createDateOfVisit);
        OrderGroup orderGroup = retryOnException(this::createOrderGroup);
        OrderResult orderResult = OrderResult.of(orderGroup, dateOfVisit);
        AppliedDiscounts appliedDiscounts = discountPolicyManager.applyDiscountPolicies(orderResult);
        printDiscountPreviewMessage(dateOfVisit);
        printCustomerOrders(orderResult);

        int totalPrice = orderResult.calculateTotalPrice();
        int totalDiscountedAmount = appliedDiscounts.calculateTotalDiscountedAmount();

        outputView.printTotalPriceBeforeDiscount(totalPrice);
        printPromotionMessage(orderResult);
        printAppliedDiscounts(appliedDiscounts);
        outputView.printTotalDiscountedAmount(totalDiscountedAmount);
        outputView.printTotalPriceAfterDiscount(totalPrice, totalDiscountedAmount);
        EventBadge eventBadge = EventBadge.findMatchingEventBadge(totalDiscountedAmount);
        outputView.printEventBadge(eventBadge);

    }

    private void printAppliedDiscounts(AppliedDiscounts appliedDiscounts) {
        AppliedDiscountsDto appliedDiscountsDto = AppliedDiscountsDto.from(appliedDiscounts);
        outputView.printAppliedDiscounts(appliedDiscountsDto);
    }

    private void printPromotionMessage(OrderResult orderResult) {
        int totalPrice = orderResult.calculateTotalPrice();
        Optional<PromotionItem> matchingPromotion = PromotionItem.findMatchingPromotion(totalPrice);
        outputView.printPromotionMessage(matchingPromotion);
    }

//    private int printTotalPriceBeforeDiscount(OrderResult orderResult) {
//        int totalPrice = orderResult.calculateTotalPrice();
//        outputView.printTotalPriceBeforeDiscount(totalPrice);
//        return totalPrice;
//    }

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
