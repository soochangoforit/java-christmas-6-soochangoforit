package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderTest {

    @Test
    void 주문에_포함될_주문_항목들이_비어있으면_예외가_발생한다() {
        List<OrderItem> emptyOrderItems = List.of();

        assertThatThrownBy(() -> new Order(emptyOrderItems))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문에_포함될_주문_항목들이_비어있지_않으면_예외가_발생하지_않는다() {
        List<OrderItem> nonEmptyOrderItems = List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1))
        );

        assertDoesNotThrow(() -> new Order(nonEmptyOrderItems));
    }

    @Test
    void 주문에_중복된_메뉴가_있으면_예외가_발생한다() {
        List<OrderItem> duplicatedOrderItems = List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1))
        );

        assertThatThrownBy(() -> new Order(duplicatedOrderItems))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문에_중복된_메뉴가_없으면_예외가_발생하지_않는다() {
        List<OrderItem> nonDuplicatedOrderItems = List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
        );

        assertDoesNotThrow(() -> new Order(nonDuplicatedOrderItems));
    }

    @Test
    void 주문에_포함된_주문_항목들의_총수량이_최대값을_넘기면_예외가_발생한다() {
        List<OrderItem> overTotalOrderItems = List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(10)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(11))
        );

        assertThatThrownBy(() -> new Order(overTotalOrderItems))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문에_포함된_주문_항목들의_총수량이_최대값을_넘기지_않으면_예외가_발생하지_않는다() {
        List<OrderItem> underTotalOrderItems = List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(10)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(10))
        );

        assertDoesNotThrow(() -> new Order(underTotalOrderItems));
    }

    @Test
    void 주문에_포함된_주문_항목들이_모두_음료면_예외가_발생한다() {
        List<OrderItem> beverageOrderItems = List.of(
                new OrderItem(Menu.ZERO_COLA, Quantity.from(1)),
                new OrderItem(Menu.RED_WINE, Quantity.from(1))
        );

        assertThatThrownBy(() -> new Order(beverageOrderItems))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문에_포함된_주문_항목들이_모두_음료가_아니면_예외가_발생하지_않는다() {
        List<OrderItem> nonBeverageOrderItems = List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
        );

        assertDoesNotThrow(() -> new Order(nonBeverageOrderItems));
    }

    @Test
    void 사용자_입력_값을_담고있는_주문_항목_매퍼_리스트로_주문을_생성할_수_있다() {
        OrderItemMapper firstOrderItemMapper = new OrderItemMapper("초코케이크", 1);
        OrderItemMapper secondOrderItemMapper = new OrderItemMapper("티본스테이크", 1);
        List<OrderItemMapper> orderItemMappers = List.of(firstOrderItemMapper, secondOrderItemMapper);

        Order order = Order.from(orderItemMappers);

        assertThat(order).usingRecursiveComparison()
                .isEqualTo(new Order(
                        List.of(
                                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
                        )
                ));
    }

    @Test
    void 주문이_특정_프로모션_상품에_대한_자격을_갖추고_있지_않으면_거짓을_응답한다() {
        Order order = new Order(List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1))
        ));

        boolean isEligibleForPromotion = order.isEligibleFor(PromotionItem.FREE_CHAMPAGNE);

        assertThat(isEligibleForPromotion).isFalse();
    }

    @Test
    void 주문이_특정_프로모션_상품에_대한_자격을_갖추고_있으면_참을_응답한다() {
        Order order = new Order(List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2)),
                new OrderItem(Menu.CHAMPAGNE, Quantity.from(1))
        ));

        boolean isEligibleForPromotion = order.isEligibleFor(PromotionItem.FREE_CHAMPAGNE);

        assertThat(isEligibleForPromotion).isTrue();
    }

    @Test
    void 주문의_총금액을_계산할_수_있다() {
        Order order = new Order(List.of(
                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1)),
                new OrderItem(Menu.CHAMPAGNE, Quantity.from(1))
        ));

        OrderAmounts orderAmounts = order.calculateOrderAmounts();

        OrderAmounts expectedOrderAmounts = OrderAmounts.from(95_000);
        assertThat(orderAmounts).usingRecursiveComparison()
                .isEqualTo(expectedOrderAmounts);
    }

    @ParameterizedTest
    @MethodSource("주문_카테고리별_수량_계산_테스트_데이터")
    void 주문에서_특정_카테고리에_속한_주문_항목들의_수량을_계산할_수_있다(List<OrderItem> orderItems, Category category,
                                               int expectedTotalQuantity) {
        Order order = new Order(orderItems);

        int totalQuantity = order.sumTotalOrderItemQuantityIn(category);

        assertThat(totalQuantity).isEqualTo(expectedTotalQuantity);
    }

    @Test
    void 외부에서_넘겨받은_주문_항목_리스트를_수정해도_주문_항목_리스트는_변경되지_않는다() {
        List<OrderItem> modifiableOrderItems = new ArrayList<>(List.of(
                new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2))
        ));
        Order order = new Order(modifiableOrderItems);

        modifiableOrderItems.clear();

        assertThat(order).usingRecursiveComparison()
                .isEqualTo(new Order(List.of(
                        new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2))
                )));
    }

    @Test
    void 외부로_넘겨준_주문_항목_리스트를_수정해도_주문_항목_리스트는_변경되지_않는다() {
        List<OrderItem> protectedOrderItems = List.of(
                new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2))
        );
        Order order = new Order(protectedOrderItems);
        List<OrderItem> modifiableOrderItems = order.getOrderItems();

        modifiableOrderItems.clear();

        assertThat(order).usingRecursiveComparison()
                .isEqualTo(new Order(List.of(
                        new OrderItem(Menu.MUSHROOM_SOUP, Quantity.from(1)),
                        new OrderItem(Menu.T_BONE_STEAK, Quantity.from(2))
                )));
    }

    private static Stream<Arguments> 주문_카테고리별_수량_계산_테스트_데이터() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new OrderItem(Menu.CHOCOLATE_CAKE, Quantity.from(1)),
                                new OrderItem(Menu.ICE_CREAM, Quantity.from(2))
                        ),
                        Category.DESSERT,
                        3
                ),
                Arguments.of(
                        List.of(
                                new OrderItem(Menu.T_BONE_STEAK, Quantity.from(1)),
                                new OrderItem(Menu.BBQ_RIBS, Quantity.from(1))
                        ),
                        Category.DESSERT,
                        0
                )
        );
    }

}
