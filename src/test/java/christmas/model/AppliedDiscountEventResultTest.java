package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AppliedDiscountEventResultTest {

    @Test
    void 할인_이벤트_적용결과에서_총할인_금액을_계산할_수_있다() {
        AppliedDiscountEventResult appliedDiscountEventResult = AppliedDiscountEventResult.from(
                Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_800),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1000)
                )
        );

        TotalDiscountAmounts totalDiscountAmounts = appliedDiscountEventResult.calculateTotalDiscountAmounts();

        assertThat(totalDiscountAmounts).isEqualTo(TotalDiscountAmounts.from(6_846));
    }

    @Test
    void 외부로_부터_받은_할인_이벤트_결과_내역이_변경되어도_객체_내부의_할인_이벤트_결과_내역은_변경되지_않는다() {
        Map<DiscountEventType, DiscountAmounts> modifiableResult = new HashMap<>(Map.of(
                DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_800),
                DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1000)
        ));
        AppliedDiscountEventResult appliedDiscountEventResult = AppliedDiscountEventResult.from(modifiableResult);

        modifiableResult.clear();

        assertThat(appliedDiscountEventResult.getDiscountEventResult())
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_800),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1000)
                ));
    }

    @Test
    void 외부로_전달된_할인_이벤트_결과_내역이_변경되어도_객체_내부의_할인_이벤트_결과_내역은_변경되지_않는다() {
        Map<DiscountEventType, DiscountAmounts> immutableResult = Map.of(
                DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_800),
                DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1000)
        );
        AppliedDiscountEventResult appliedDiscountEventResult = AppliedDiscountEventResult.from(immutableResult);
        Map<DiscountEventType, DiscountAmounts> modifiableResult = appliedDiscountEventResult.getDiscountEventResult();

        modifiableResult.clear();

        assertThat(appliedDiscountEventResult.getDiscountEventResult())
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(1_800),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(4_046),
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1000)
                ));
    }
}
