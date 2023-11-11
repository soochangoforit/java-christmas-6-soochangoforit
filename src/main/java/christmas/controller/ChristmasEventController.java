package christmas.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import christmas.dto.request.OrderInfoDto;
import christmas.dto.request.VisitDayDto;
import christmas.dto.response.AppliedDiscountsDto;
import christmas.dto.response.DateOfVisitInfoDto;
import christmas.dto.response.OrderResultDto;
import christmas.model.AppliedDiscounts;
import christmas.model.DiscountPolicyManager;
import christmas.model.EventBadge;
import christmas.model.OrderGroup;
import christmas.model.OrderInfo;
import christmas.model.OrderResult;
import christmas.model.PromotionItem;
import christmas.model.VisitDate;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ChristmasEventController {
    private final InputView inputView;
    private final OutputView outputView;
    private final DiscountPolicyManager discountPolicyManager;

    public ChristmasEventController(InputView inputView, OutputView outputView,
                                    DiscountPolicyManager discountPolicyManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.discountPolicyManager = discountPolicyManager;
    }

    public void run() {
        outputView.printWelcomeMessage();
        VisitDate visitDate = retryOnException(this::createDateOfVisit);
        OrderGroup orderGroup = retryOnException(this::createOrderGroup);
        OrderResult orderResult = OrderResult.of(orderGroup, visitDate);
        AppliedDiscounts appliedDiscounts = discountPolicyManager.applyDiscountPolicies(orderResult);
        printDiscountPreviewMessage(visitDate);
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

    private void printDiscountPreviewMessage(VisitDate visitDate) {
        DateOfVisitInfoDto dateOfVisitInfoDto = DateOfVisitInfoDto.from(visitDate);
        outputView.printDiscountPreviewMessage(dateOfVisitInfoDto);
    }

    private void printCustomerOrders(OrderResult orderResult) {
        OrderResultDto orderResultDto = OrderResultDto.from(orderResult);
        outputView.printOrderResult(orderResultDto);
    }

    private OrderGroup createOrderGroup() {
        List<OrderInfoDto> orderInfoDtos = retryOnException(this::readCustomerOrders);
        List<OrderInfo> orderInfos = convertFrom(orderInfoDtos);

        return OrderGroup.from(orderInfos);
    }

    private List<OrderInfo> convertFrom(List<OrderInfoDto> orderInfoDtos) {
        List<OrderInfo> orderInfos = orderInfoDtos.stream()
                .map(OrderInfo::of)
                .toList();

        return orderInfos;
    }

    private List<OrderInfoDto> readCustomerOrders() {
        return inputView.readCustomerOrders();
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
