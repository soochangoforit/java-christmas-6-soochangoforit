package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TotalBenefitAmountsTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1_000})
    void 총혜택_금액은_0원_이상을_가지면_예외가_발생하지_않는다(int amounts) {
        assertDoesNotThrow(() -> TotalBenefitAmounts.from(amounts));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1000})
    void 총혜택_금액이_0원_보다_작은_값을_가지면_예외가_발생한다(int amounts) {
        assertThatThrownBy(() -> TotalBenefitAmounts.from(amounts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {10_000, 10_001})
    void 총혜택_금액이_최소_자격요건을_만족하면_참을_반환한다(int amounts) {
        TotalBenefitAmounts totalBenefitAmounts = TotalBenefitAmounts.from(amounts);

        boolean isEligible = totalBenefitAmounts.isEligibleFor(10_000);

        assertThat(isEligible).isTrue();
    }

    @Test
    void 총혜택_금액이_최소_자격요건을_만족하지_않으면_거짓을_반환한다() {
        TotalBenefitAmounts totalBenefitAmounts = TotalBenefitAmounts.from(9_999);

        boolean isEligible = totalBenefitAmounts.isEligibleFor(10_000);

        assertThat(isEligible).isFalse();
    }

    @Test
    void 총혜택_금액이_같으면_서로_같은_객체이다() {
        TotalBenefitAmounts totalBenefitAmounts = TotalBenefitAmounts.from(10_000);
        TotalBenefitAmounts sameTotalBenefitAmounts = TotalBenefitAmounts.from(10_000);

        assertThat(totalBenefitAmounts).isEqualTo(sameTotalBenefitAmounts);
    }

    @Test
    void 총혜택_금액이_다르면_서로_다른_객체이다() {
        TotalBenefitAmounts totalBenefitAmounts = TotalBenefitAmounts.from(10_000);
        TotalBenefitAmounts differentTotalBenefitAmounts = TotalBenefitAmounts.from(9_999);

        assertThat(totalBenefitAmounts).isNotEqualTo(differentTotalBenefitAmounts);
    }

    @Test
    void 총혜택_금액이_같으면_서로_같은_해시코드를_가진다() {
        TotalBenefitAmounts totalBenefitAmounts = TotalBenefitAmounts.from(10_000);
        TotalBenefitAmounts sameTotalBenefitAmounts = TotalBenefitAmounts.from(10_000);

        assertThat(totalBenefitAmounts).hasSameHashCodeAs(sameTotalBenefitAmounts);
    }
}
