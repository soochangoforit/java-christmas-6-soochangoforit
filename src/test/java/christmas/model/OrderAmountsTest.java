package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderAmountsTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -1000})
    void 총주문금액이_0원_보다_작은_값을_가지면_예외가_발생한다(int amounts) {
        assertThatThrownBy(() -> OrderAmounts.from(amounts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2000})
    void 총주문금액이_0원_이상의_값을_가지면_예외가_발생하지_않는다(int amounts) {
        assertDoesNotThrow(() -> OrderAmounts.from(amounts));
    }

    @Test
    void 총주문금액이_특정한_최소_금액_자격요건을_만족하면_참을_반환한다() {
        OrderAmounts orderAmounts = OrderAmounts.from(10_000);

        boolean isEligible = orderAmounts.isEligibleFor(10_000);

        assertThat(isEligible).isTrue();
    }

    @Test
    void 총주문금액이_특정한_최소_금액_자격요건을_만족하지_않으면_거짓을_반환한다() {
        OrderAmounts orderAmounts = OrderAmounts.from(9_999);

        boolean isEligible = orderAmounts.isEligibleFor(10_000);

        assertThat(isEligible).isFalse();
    }

    @Test
    void 총주문금액에서_총할인_금액을_차감할_수_있다() {
        OrderAmounts orderAmounts = OrderAmounts.from(10_000);
        DiscountAmounts totalDiscountAmounts = DiscountAmounts.from(1_000);

        OrderAmounts orderAmountsAfterDiscount = orderAmounts.deductTotalDiscountAmounts(totalDiscountAmounts);

        OrderAmounts expectedOrderAmounts = OrderAmounts.from(9_000);
        assertThat(orderAmountsAfterDiscount).usingRecursiveComparison()
                .isEqualTo(expectedOrderAmounts);
    }

    @Test
    void 총주문금액만큼_할인_혜택금액을_차감함으로서_총주문금액은_0원을_가질_수_있다() {
        OrderAmounts orderAmounts = OrderAmounts.from(10_000);
        DiscountAmounts totalDiscountAmounts = DiscountAmounts.from(10_000);

        OrderAmounts orderAmountsAfterDiscount = orderAmounts.deductTotalDiscountAmounts(totalDiscountAmounts);

        OrderAmounts expectedOrderAmounts = OrderAmounts.from(0);
        assertThat(orderAmountsAfterDiscount).usingRecursiveComparison()
                .isEqualTo(expectedOrderAmounts);
    }

    @Test
    void 총주문금액이_같은_경우_같은_객체이다() {
        OrderAmounts orderAmounts = OrderAmounts.from(10_000);
        OrderAmounts sameOrderAmounts = OrderAmounts.from(10_000);

        assertThat(orderAmounts).isEqualTo(sameOrderAmounts);
    }

    @Test
    void 총주문금액이_다른_경우_다른_객체이다() {
        OrderAmounts orderAmounts = OrderAmounts.from(10_000);
        OrderAmounts differentOrderAmounts = OrderAmounts.from(9_999);

        assertThat(orderAmounts).isNotEqualTo(differentOrderAmounts);
    }

    @Test
    void 총주문금액이_같은_경우_같은_해시코드를_가진다() {
        OrderAmounts orderAmounts = OrderAmounts.from(10_000);
        OrderAmounts sameOrderAmounts = OrderAmounts.from(10_000);

        assertThat(orderAmounts).hasSameHashCodeAs(sameOrderAmounts);
    }

}
