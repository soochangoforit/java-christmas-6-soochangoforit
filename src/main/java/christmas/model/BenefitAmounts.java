package christmas.model;

public class BenefitAmounts {
    private final int amounts;

    public BenefitAmounts(int amounts) {
        this.amounts = amounts;
    }

    public static BenefitAmounts noBenefit() {
        return new BenefitAmounts(0);
    }

    public static BenefitAmounts from(int amounts) {
        return new BenefitAmounts(amounts);
    }

    public boolean hasNotAnyBenefit() {
        return amounts == 0;
    }

    public boolean hasAnyBenefit() {
        return amounts > 0;
    }

    public boolean isGreaterThanOrEqualTo(int amounts) {
        return this.amounts >= amounts;
    }

    public int getAmounts() {
        return amounts;
    }
}
