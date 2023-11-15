package christmas;

import java.util.Map;
import christmas.controller.ChristmasEventController;
import christmas.model.ChristmasDdayDiscount;
import christmas.model.DiscountEventManager;
import christmas.model.DiscountEventType;
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
        DiscountEventManager discountEventManager = createDiscountEventManager();
        ChristmasEventController christmasEventController = new ChristmasEventController(inputView, outputView,
                discountEventManager);
        christmasEventController.run();
    }

    private static DiscountEventManager createDiscountEventManager() {
        return DiscountEventManager.from(
                Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, new ChristmasDdayDiscount(),
                        DiscountEventType.WEEKDAY_EVENT, new WeekdayDiscount(),
                        DiscountEventType.WEEKEND_EVENT, new WeekendDiscount(),
                        DiscountEventType.SPECIAL_EVENT, new SpecialDiscount(),
                        DiscountEventType.PROMOTION_EVENT, new PromotionDiscount()
                )
        );
    }
}
