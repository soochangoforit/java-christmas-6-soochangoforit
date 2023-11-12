package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import christmas.dto.request.OrderItemInfoDto;

class OrderItemMapperTest {

    @Test
    void 사용자가_입력한_주문_항목_데이터를_담고있는_Dto를_통해_Mapper를_생성할_수_있다() {
        OrderItemInfoDto orderItemDto = new OrderItemInfoDto("양송이수프", 1);

        OrderItemMapper orderItemMapper = OrderItemMapper.from(orderItemDto);

        assertThat(orderItemMapper).usingRecursiveComparison()
                .isEqualTo(new OrderItemMapper("양송이수프", 1));
    }
}
