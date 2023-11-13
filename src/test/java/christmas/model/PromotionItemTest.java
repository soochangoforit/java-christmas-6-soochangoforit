package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionItemTest {

    @ParameterizedTest
    @ValueSource(ints = {120_000, 130_000})
    void 총주문_금액에_따른_프로모션_아이템을_찾을_수_있다(int amounts) {
        OrderAmounts orderAmounts = OrderAmounts.from(amounts);

        PromotionItem actualPromotionItem = PromotionItem.findMatchingPromotion(orderAmounts);

        assertThat(actualPromotionItem).isEqualTo(PromotionItem.FREE_CHAMPAGNE);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100_000, 119_999})
    void 총주문_금액에_따른_프로모션_아이템을_찾을_수_없으면_기본값을_반환한다(int amounts) {
        OrderAmounts orderAmounts = OrderAmounts.from(amounts);

        PromotionItem actualPromotionItem = PromotionItem.findMatchingPromotion(orderAmounts);

        assertThat(actualPromotionItem).isEqualTo(PromotionItem.NONE);
    }

    @ParameterizedTest
    @ValueSource(ints = {120_000, 130_000})
    void 프로모션_아이템이_총주문_금액을_보고_자격을_갖춰졌다_판단되면_참을_반환한다(int amounts) {
        OrderAmounts orderAmounts = OrderAmounts.from(amounts);

        boolean isEligibleForPromotion = PromotionItem.FREE_CHAMPAGNE.isEligibleFor(orderAmounts);

        assertThat(isEligibleForPromotion).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {100_000, 119_999})
    void 프로모션_아이템이_총주문_금액을_보고_자격을_갖추지_못했다_판단되면_거짓을_반환한다(int amounts) {
        OrderAmounts orderAmounts = OrderAmounts.from(amounts);

        boolean isEligibleForPromotion = PromotionItem.FREE_CHAMPAGNE.isEligibleFor(orderAmounts);

        assertThat(isEligibleForPromotion).isFalse();
    }

    @ParameterizedTest
    @MethodSource("프로모션_아이템_및_할인_금액_제공_테스트_케이스")
    void 프로모션_아이템을_제공함으로써_할인_금액을_계산할_수_있다(PromotionItem promotionItem, int expectedDiscountAmounts) {
        int discountAmounts = promotionItem.calculateDiscountAmounts();

        assertThat(discountAmounts).isEqualTo(expectedDiscountAmounts);
    }

    @Test
    void 서로_같은_값을_가지는_프로모션_아이템은_같은_객체이다() {
        PromotionItem actualPromotion = PromotionItem.FREE_CHAMPAGNE;
        PromotionItem expectedPromotion = PromotionItem.FREE_CHAMPAGNE;

        assertThat(actualPromotion).isSameAs(expectedPromotion);
    }

    @Test
    void 서로_다른_값을_가지는_프로모션_아이템은_다른_객체이다() {
        PromotionItem actualPromotion = PromotionItem.FREE_CHAMPAGNE;
        PromotionItem expectedPromotion = PromotionItem.NONE;

        assertThat(actualPromotion).isNotSameAs(expectedPromotion);
    }

    @Test
    void 서로_같은_값을_가지는_프로모션_아이템은_해시코드가_같다() {
        PromotionItem actualPromotion = PromotionItem.FREE_CHAMPAGNE;
        PromotionItem expectedPromotion = PromotionItem.FREE_CHAMPAGNE;

        assertThat(actualPromotion).hasSameHashCodeAs(expectedPromotion);
    }

    private static Stream<Arguments> 프로모션_아이템_및_할인_금액_제공_테스트_케이스() {
        return Stream.of(
                Arguments.of(PromotionItem.FREE_CHAMPAGNE, 25_000),
                Arguments.of(PromotionItem.NONE, 0)
        );
    }
}
