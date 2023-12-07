package christmas.model;

import java.util.Map;

public class BenefitResult {
    private final Map<BenefitType, BenefitAmounts> result;

    public BenefitResult(Map<BenefitType, BenefitAmounts> result) {
        this.result = result;
    }

    public static BenefitResult from(Map<BenefitType, BenefitAmounts> result) {
        return new BenefitResult(result);
    }
}
