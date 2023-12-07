package christmas.model;

import java.util.EnumMap;
import java.util.Map;

public class BenefitResult {
    private final Map<BenefitType, BenefitAmounts> result;

    public BenefitResult(Map<BenefitType, BenefitAmounts> result) {
        this.result = result;
    }

    public static BenefitResult from(Map<BenefitType, BenefitAmounts> result) {
        return new BenefitResult(result);
    }

    public static BenefitResult empty() {
        return new BenefitResult(Map.of());
    }

    public boolean hasNotAnyBenefit() {
        return result.values().stream()
                .allMatch(BenefitAmounts::hasNotAnyBenefit);
    }

    public BenefitAmounts calculateTotalBenefitAmounts() {
        int benefitAmounts = calculateBenefitAmounts();

        return BenefitAmounts.from(benefitAmounts);
    }

    private int calculateBenefitAmounts() {
        return result.values().stream()
                .mapToInt(BenefitAmounts::getAmounts)
                .sum();
    }

    public int calculateTotalDiscountAmounts() {
        return result.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isDiscountEvent())
                .mapToInt(entry -> entry.getValue().getAmounts())
                .sum();
    }

    public Map<BenefitType, BenefitAmounts> getResult() {
        return new EnumMap<>(result);
    }
}
