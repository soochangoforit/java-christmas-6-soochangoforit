package christmas.model;

import java.util.Objects;

public final class TotalBenefitAmounts {
    private static final String INVALID_BENEFIT_AMOUNT = "[ERROR] 총 혜택 금액은 %d원 이상이어야 합니다.";
    private static final int ZERO_TOTAL_BENEFIT_AMOUNT = 0;
    private final int amounts;

    private TotalBenefitAmounts(int amounts) {
        validateAmounts(amounts);
        this.amounts = amounts;
    }

    private void validateAmounts(int amounts) {
        if (amounts < ZERO_TOTAL_BENEFIT_AMOUNT) {
            String exceptionMessage = String.format(INVALID_BENEFIT_AMOUNT, ZERO_TOTAL_BENEFIT_AMOUNT);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static TotalBenefitAmounts from(int amounts) {
        return new TotalBenefitAmounts(amounts);
    }

    public boolean isEligibleFor(int minimumAmountForEligibility) {
        return this.amounts >= minimumAmountForEligibility;
    }

    public int getAmounts() {
        return amounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TotalBenefitAmounts that = (TotalBenefitAmounts) o;
        return amounts == that.amounts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amounts);
    }
}
