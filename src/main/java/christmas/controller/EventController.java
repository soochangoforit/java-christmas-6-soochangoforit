package christmas.controller;

import java.util.List;
import java.util.function.Supplier;
import christmas.dto.request.OrderItemDto;
import christmas.model.BenefitResult;
import christmas.model.BenefitStorage;
import christmas.model.Order;
import christmas.model.OrderFactory;
import christmas.model.OrderInfo;
import christmas.model.PromotionItem;
import christmas.model.VisitDate;
import christmas.view.InputView;
import christmas.view.OutputView;

public class EventController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BenefitStorage benefitStorage;

    public EventController(InputView inputView, OutputView outputView, BenefitStorage benefitStorage) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.benefitStorage = benefitStorage;
    }

    public void run() {
        outputView.printStartMessage();
        VisitDate visitDate = fetch(this::readVisitDate);
        Order order = fetch(this::createOrder);
        OrderInfo orderInfo = OrderInfo.from(visitDate, order);
        outputView.printPreviewMessage(orderInfo);
        outputView.printOrder(orderInfo);
        int orderAmounts = orderInfo.calculateOrderAmounts();
        outputView.printOrderAmounts(orderAmounts);
        outputView.printPromotionItem(PromotionItem.determinePromotionItem(orderAmounts));
        BenefitResult result = benefitStorage.applyBenefits(orderInfo);
        outputView.printBenefitResult(result);
    }

    private Order createOrder() {
        List<OrderItemDto> orderItemDtos = inputView.readOrder();
        return OrderFactory.create(orderItemDtos);
    }

    private VisitDate readVisitDate() {
        int rawVisitDay = inputView.readVisitDay();
        return VisitDate.from(rawVisitDay);
    }

    private <T> T fetch(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            return fetch(supplier);
        }
    }
}
