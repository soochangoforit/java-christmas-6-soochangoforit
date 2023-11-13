package christmas.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import christmas.model.AppliedDiscountEventResult;
import christmas.model.DiscountAmounts;
import christmas.model.DiscountEventType;

class AppliedDiscountEventResultDtoTest {

    @Test
    void 할인_이벤트_결과_내역에서_할인_금액이_0원인_경우는_포함하지_않는다() {
        AppliedDiscountEventResult discountEventResult = AppliedDiscountEventResult.from(
                Map.of(
                        DiscountEventType.SPECIAL_EVENT, DiscountAmounts.from(1_000),
                        DiscountEventType.CHRISTMAS_DDAY_EVENT, DiscountAmounts.from(0),
                        DiscountEventType.WEEKDAY_EVENT, DiscountAmounts.from(0)
                )
        );
        AppliedDiscountEventResultDto eventResultDto = AppliedDiscountEventResultDto.from(discountEventResult);

        assertThat(eventResultDto.getDiscountEventResult())
                .doesNotContainKeys(
                        DiscountEventType.CHRISTMAS_DDAY_EVENT.getName(),
                        DiscountEventType.WEEKDAY_EVENT.getName()
                );
    }
}
