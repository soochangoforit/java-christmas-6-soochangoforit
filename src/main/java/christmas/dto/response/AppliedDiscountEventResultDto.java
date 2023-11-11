package christmas.dto.response;

import java.util.HashMap;
import java.util.Map;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountAmounts;
import christmas.model.DiscountEventType;

public class AppliedDiscountEventResultDto {
    private static final int ZERO_AMOUNT = 0;
    private final Map<String, Integer> discountEventResult;

    private AppliedDiscountEventResultDto(Map<String, Integer> discountEventResult) {
        this.discountEventResult = discountEventResult;
    }

    public static AppliedDiscountEventResultDto from(AppliedDiscountEventResult appliedDiscountEventResult) {
        Map<DiscountEventType, DiscountAmounts> eventResult = appliedDiscountEventResult.getDiscountEventResult();
        Map<String, Integer> discountEventResult = new HashMap<>();

        eventResult.forEach((discountType, discountedAmount) -> {
            if (discountedAmount.getAmounts() > ZERO_AMOUNT) {
                discountEventResult.put(discountType.getName(), discountedAmount.getAmounts());
            }
        });

        return new AppliedDiscountEventResultDto(discountEventResult);
    }

    public Map<String, Integer> getDiscountEventResult() {
        return discountEventResult;
    }
}
