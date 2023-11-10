package christmas;

import java.util.Map;
import christmas.controller.ChristmasEventController;
import christmas.model.ChristmasDdayDiscount;
import christmas.model.DiscountPolicyManager;
import christmas.model.DiscountType;
import christmas.model.OrderService;
import christmas.model.PromotionDiscount;
import christmas.model.SpecialDiscount;
import christmas.model.WeekdayDiscount;
import christmas.model.WeekendDiscount;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        OrderService orderService = new OrderService();
        DiscountPolicyManager policies = DiscountPolicyManager.from(
                Map.of(DiscountType.CHRISTMAS_DDAY, new ChristmasDdayDiscount()
                        , DiscountType.WEEKDAY, new WeekdayDiscount()
                        , DiscountType.WEEKEND, new WeekendDiscount()
                        , DiscountType.SPECIAL, new SpecialDiscount()
                        , DiscountType.PROMOTION, new PromotionDiscount()));
        ChristmasEventController christmasEventController = new ChristmasEventController(inputView, outputView,
                orderService, policies);
        christmasEventController.run();
    }
}
