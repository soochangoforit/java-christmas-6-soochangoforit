package christmas.dto.response;

import java.util.HashMap;
import java.util.Map;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountAmounts;
import christmas.model.DiscountEventType;

public class AppliedDiscountEventResultDto {
    private final Map<String, Integer> discountEventResult;

    private AppliedDiscountEventResultDto(Map<String, Integer> discountEventResult) {
        this.discountEventResult = discountEventResult;
    }

    public static AppliedDiscountEventResultDto from(AppliedDiscountEventResult appliedDiscountEventResult) {
        Map<DiscountEventType, DiscountAmounts> eventResult = appliedDiscountEventResult.getDiscountEventResult();

        Map<String, Integer> discountEventResult = generateAppliedDiscountEventResult(eventResult);

        return new AppliedDiscountEventResultDto(discountEventResult);
    }

    private static Map<String, Integer> generateAppliedDiscountEventResult(
            Map<DiscountEventType, DiscountAmounts> eventResult) {
        Map<String, Integer> discountEventResult = new HashMap<>();
        eventResult.forEach((discountEventType, discountAmounts) -> {
            putDiscountEventResult(discountEventType, discountAmounts, discountEventResult);
        });

        return discountEventResult;
    }

    private static void putDiscountEventResult(DiscountEventType discountEventType, DiscountAmounts discountAmounts,
                                               Map<String, Integer> discountEventResult) {
        if (discountAmounts.isOverZeroAmounts()) {
            discountEventResult.put(discountEventType.getName(), discountAmounts.getAmounts());
        }
    }

    public Map<String, Integer> getDiscountEventResult() {
        return discountEventResult;
    }
}
