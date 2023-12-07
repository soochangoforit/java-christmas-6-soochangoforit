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
}
