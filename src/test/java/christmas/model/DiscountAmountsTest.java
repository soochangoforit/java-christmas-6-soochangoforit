package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountsTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -1000})
    void 할임금액은_음수값을_가지면_예외가_발생한다(int negativeAmounts) {
        assertThatThrownBy(() -> DiscountAmounts.from(negativeAmounts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1_000})
    void 할인금액은_0원_이상을_가지면_예외가_발생하지_않는다(int amounts) {
        assertDoesNotThrow(() -> DiscountAmounts.from(amounts));
    }

    @Test
    void 할임금액이_없으면_0원을_갖는다() {
        DiscountAmounts discountAmounts = DiscountAmounts.noDiscount();

        assertThat(discountAmounts).isEqualTo(DiscountAmounts.from(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 1_000})
    void 할인금액이_0원_보다_큰_값을_가지면_참을_반환한다(int amounts) {
        DiscountAmounts discountAmounts = DiscountAmounts.from(amounts);

        boolean isOverZero = discountAmounts.isOverZeroAmounts();

        assertThat(isOverZero).isTrue();
    }

    @Test
    void 총주문금액에서_할인금액을_뺀_금액을_계산할_수_있다() {
        int orderAmounts = 10_000;
        DiscountAmounts totalDiscountAmounts = DiscountAmounts.from(1_000);

        int orderAmountsAfterDiscount = totalDiscountAmounts.calculateOrderAmountsAfterDiscount(orderAmounts);

        assertThat(orderAmountsAfterDiscount).isEqualTo(9_000);
    }

    @Test
    void 할인금액이_0원_아하의_값을_가지면_거짓을_반환한다() {
        DiscountAmounts discountAmounts = DiscountAmounts.from(0);

        boolean isOverZero = discountAmounts.isOverZeroAmounts();

        assertThat(isOverZero).isFalse();
    }

    @Test
    void 할인_금액이_같은_경우_서로_같은_객체이다() {
        DiscountAmounts discountAmounts = DiscountAmounts.from(1_000);
        DiscountAmounts sameDiscountAmounts = DiscountAmounts.from(1_000);

        assertThat(discountAmounts).isEqualTo(sameDiscountAmounts);
    }

    @Test
    void 할인_금액이_다른_경우_서로_다른_객체이다() {
        DiscountAmounts discountAmounts = DiscountAmounts.from(1_000);
        DiscountAmounts differentDiscountAmounts = DiscountAmounts.from(2_000);

        assertThat(discountAmounts).isNotEqualTo(differentDiscountAmounts);
    }

    @Test
    void 할인_금액이_같으면_서로_같은_해시코드를_가진다() {
        DiscountAmounts discountAmounts = DiscountAmounts.from(1_000);
        DiscountAmounts sameDiscountAmounts = DiscountAmounts.from(1_000);

        assertThat(discountAmounts).hasSameHashCodeAs(sameDiscountAmounts);
    }
}
