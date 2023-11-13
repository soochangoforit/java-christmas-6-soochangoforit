package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TotalDiscountAmountsTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1_000})
    void 총할인_금액은_0원_이상을_가지면_예외가_발생하지_않는다(int amounts) {
        assertDoesNotThrow(() -> TotalDiscountAmounts.from(amounts));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1000})
    void 총할인_금액이_0원_보다_작은_값을_가지면_예외가_발생한다(int amounts) {
        assertThatThrownBy(() -> TotalDiscountAmounts.from(amounts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 총할인_금액을_총주문_금액에서_차감할_수_있다() {
        int orderAmounts = 10_000;
        TotalDiscountAmounts totalDiscountAmounts = TotalDiscountAmounts.from(1_000);

        int orderAmountsAfterDiscount = totalDiscountAmounts.calculateOrderAmountsAfterDiscount(orderAmounts);

        int expectedOrderAmountsAfterDiscount = 9_000;
        assertThat(orderAmountsAfterDiscount).isEqualTo(expectedOrderAmountsAfterDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {10_000, 10_001})
    void 총할인_금액이_최소_자격요건을_만족하면_참을_반환한다(int amounts) {
        TotalDiscountAmounts totalDiscountAmounts = TotalDiscountAmounts.from(amounts);

        boolean isEligible = totalDiscountAmounts.isEligibleFor(10_000);

        assertThat(isEligible).isTrue();
    }

    @Test
    void 총할인_금액이_최소_자격요건을_만족하지_않으면_거짓을_반환한다() {
        TotalDiscountAmounts totalDiscountAmounts = TotalDiscountAmounts.from(9_999);

        boolean isEligible = totalDiscountAmounts.isEligibleFor(10_000);

        assertThat(isEligible).isFalse();
    }

    @Test
    void 총할인_금액이_같으면_서로_같은_객체이다() {
        TotalDiscountAmounts totalDiscountAmounts = TotalDiscountAmounts.from(10_000);
        TotalDiscountAmounts sameTotalDiscountAmounts = TotalDiscountAmounts.from(10_000);

        assertThat(totalDiscountAmounts).isEqualTo(sameTotalDiscountAmounts);
    }

    @Test
    void 총할인_금액이_다르면_서로_다른_객체이다() {
        TotalDiscountAmounts totalDiscountAmounts = TotalDiscountAmounts.from(10_000);
        TotalDiscountAmounts differentTotalDiscountAmounts = TotalDiscountAmounts.from(9_999);

        assertThat(totalDiscountAmounts).isNotEqualTo(differentTotalDiscountAmounts);
    }

    @Test
    void 총할인_금액이_같으면_서로_같은_해시코드를_가진다() {
        TotalDiscountAmounts totalDiscountAmounts = TotalDiscountAmounts.from(10_000);
        TotalDiscountAmounts sameTotalDiscountAmounts = TotalDiscountAmounts.from(10_000);

        assertThat(totalDiscountAmounts).hasSameHashCodeAs(sameTotalDiscountAmounts);
    }
}
