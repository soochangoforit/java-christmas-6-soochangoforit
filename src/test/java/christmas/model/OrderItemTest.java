package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderItemTest {

    @Test
    void 정상적인_사용자_주문_입력값을_갖는_매퍼클래스를_통해서_주문항목을_생성하면_예외가_발생하지_않는다() {
        OrderItemMapper orderItemMapper = new OrderItemMapper("초코케이크", 1);

        assertDoesNotThrow(() -> OrderItem.from(orderItemMapper));
    }

    @Test
    void 사용자_주문_항목_입력_값으로_주문_항목을_생성하는_경우_존재하지_않는_메뉴_이름이면_예외가_발생한다() {
        assertThatThrownBy(() -> new OrderItem("없는 메뉴", 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void 사용자_주문_항목_입력_값으로_주문_항목을_생성하는_경우_음수_및_0값이면_예외가_발생한다(int quantity) {
        assertThatThrownBy(() -> new OrderItem("초코케이크", quantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 사용자_주문_항목_입력_값으로_올바른_메뉴와_수량으로_주문_항목을_생성하는_경우_예외가_발생하지_않는다() {
        assertDoesNotThrow(() -> new OrderItem("초코케이크", 1));
    }

    @ParameterizedTest
    @MethodSource("메뉴_수량_예상금액")
    void 주문_항목으로부터_지불해야할_금액을_계산할_수_있다(Menu menu, int quantity, int expectedTotalOrderItemAmounts) {
        OrderItem orderItem = new OrderItem(menu, Quantity.from(quantity));

        int totalOrderItemAmounts = orderItem.calculateAmounts();

        assertThat(totalOrderItemAmounts).isEqualTo(expectedTotalOrderItemAmounts);
    }

    @ParameterizedTest
    @MethodSource("메뉴_메뉴와일치하는카테고리")
    void 주문_항목이_특정_카테고리에_속하는_경우는_참을_응답한다(Menu menu, Category category) {
        OrderItem orderItem = new OrderItem(menu, Quantity.from(1));

        boolean belongsToCategory = orderItem.belongsTo(category);

        assertThat(belongsToCategory).isTrue();
    }

    @ParameterizedTest
    @MethodSource("메뉴_메뉴와일치하지않는카테고리")
    void 주문_항목이_카테고리에_속하지_않는_경우는_거짓을_응답한다(Menu menu, Category category) {
        OrderItem orderItem = new OrderItem(menu, Quantity.from(1));

        boolean belongsToCategory = orderItem.belongsTo(category);

        assertThat(belongsToCategory).isFalse();
    }

    @Test
    void 주문_항목이_음료_카테고리에_속하는_경우_참을_응답한다() {
        OrderItem orderItem = new OrderItem(Menu.RED_WINE, Quantity.from(1));

        boolean isBeverage = orderItem.isBeverage();

        assertThat(isBeverage).isTrue();
    }

    @Test
    void 주문_항목이_음료_카테고리에_속하지_않는_경우_거짓을_응답한다() {
        OrderItem orderItem = new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1));

        boolean isBeverage = orderItem.isBeverage();

        assertThat(isBeverage).isFalse();
    }

    private static Stream<Arguments> 메뉴_메뉴와일치하지않는카테고리() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, Category.MAIN),
                Arguments.of(Menu.T_BONE_STEAK, Category.DESSERT),
                Arguments.of(Menu.CHOCOLATE_CAKE, Category.BEVERAGE),
                Arguments.of(Menu.RED_WINE, Category.APPETIZER)
        );
    }

    private static Stream<Arguments> 메뉴_메뉴와일치하는카테고리() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, Category.APPETIZER),
                Arguments.of(Menu.T_BONE_STEAK, Category.MAIN),
                Arguments.of(Menu.CHOCOLATE_CAKE, Category.DESSERT),
                Arguments.of(Menu.RED_WINE, Category.BEVERAGE)
        );
    }

    private static Stream<Arguments> 메뉴_수량_예상금액() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, 1, 6_000),
                Arguments.of(Menu.T_BONE_STEAK, 2, 110_000),
                Arguments.of(Menu.ICE_CREAM, 3, 15_000)
        );
    }

}
