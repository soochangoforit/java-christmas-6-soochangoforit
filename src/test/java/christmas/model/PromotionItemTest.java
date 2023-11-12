package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PromotionItemTest {

    @Test
    void 총주문_금액에_따른_프로모션_아이템을_찾을_수_있다() {
        OrderAmounts orderAmounts = OrderAmounts.from(120_000);

        PromotionItem actualPromotionItem = PromotionItem.findMatchingPromotion(orderAmounts);

        assertThat(actualPromotionItem).isEqualTo(PromotionItem.FREE_CHAMPAGNE);
    }

    @Test
    void 총주문_금액에_따른_프로모션_아이템을_찾을_수_없으면_NONE을_반환한다() {
        OrderAmounts orderAmounts = OrderAmounts.from(119_999);

        PromotionItem actualPromotionItem = PromotionItem.findMatchingPromotion(orderAmounts);

        assertThat(actualPromotionItem).isEqualTo(PromotionItem.NONE);
    }

    @Test
    void 프로모션_아이템이_주문_금액에_따라_자격을_갖추면_참을_반환한다() {
        OrderAmounts orderAmounts = OrderAmounts.from(120_000);

        boolean isEligibleForPromotion = PromotionItem.FREE_CHAMPAGNE.isEligibleFor(orderAmounts);

        assertThat(isEligibleForPromotion).isTrue();
    }

    @Test
    void 프로모션_아이템이_주문_금액에_따라_자격을_갖추지_않으면_거짓을_반환한다() {
        OrderAmounts orderAmounts = OrderAmounts.from(119_999);

        boolean isEligibleForPromotion = PromotionItem.FREE_CHAMPAGNE.isEligibleFor(orderAmounts);

        assertThat(isEligibleForPromotion).isFalse();
    }

    @Test
    void 프로모션_아이템을_제공함으로써_할인_금액을_계산할_수_있다() {
        int discountAmounts = PromotionItem.FREE_CHAMPAGNE.calculateDiscountAmounts();

        assertThat(discountAmounts).isEqualTo(25_000);
    }

    @Test
    void 프로모션_아이템으로_제공할_것이_없으면_할인_금액은_0이다() {
        int discountAmounts = PromotionItem.NONE.calculateDiscountAmounts();

        assertThat(discountAmounts).isEqualTo(0);
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
}
