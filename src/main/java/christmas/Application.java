package christmas;

import java.util.Map;
import christmas.controller.EventController;
import christmas.model.BenefitStorage;
import christmas.model.BenefitType;
import christmas.model.ChristmasEvent;
import christmas.model.PromotionEvent;
import christmas.model.SpecialEvent;
import christmas.model.WeekdayEvent;
import christmas.model.WeekendEvent;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BenefitStorage benefitStorage = createBenefitStorage();
        EventController eventController = new EventController(inputView, outputView, benefitStorage);
        eventController.run();
    }

    private static BenefitStorage createBenefitStorage() {
        return new BenefitStorage(Map.of(
                BenefitType.CHRISTMAS_DISCOUNT_EVENT, new ChristmasEvent(),
                BenefitType.WEEKDAY_DISCOUNT_EVENT, new WeekdayEvent(),
                BenefitType.WEEKEND_DISCOUNT_EVENT, new WeekendEvent(),
                BenefitType.SPECIAL_DISCOUNT_EVENT, new SpecialEvent(),
                BenefitType.PROMOTION_EVENT, new PromotionEvent()

        ));
    }
}
