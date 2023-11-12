package christmas.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MenuTest {

    @ParameterizedTest
    @MethodSource("메뉴_이름_예상_메뉴")
    void 메뉴_이름으로_메뉴를_찾을_수_있다(String menuName, Menu expectedMenu) {
        Menu resultMenu = Menu.from(menuName);

        assertThat(resultMenu).isEqualTo(expectedMenu);
    }

    @Test
    void 존재하지_않는_메뉴_이름으로_예외_발생() {
        String invalidMenuName = "없는 메뉴";

        assertThatThrownBy(() -> Menu.from(invalidMenuName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("메뉴_수량_예상_금액")
    void 메뉴별_수량에_따른_지불_금액을_계산_할_수_있다(Menu menu, Quantity quantity, int expectedTotal) {
        int totalAmount = menu.calculateAmounts(quantity);

        assertThat(totalAmount).isEqualTo(expectedTotal);
    }

    @ParameterizedTest
    @MethodSource("메뉴_수량_예상_할인금액")
    void 메뉴별_수량에_따른_프로모션_할인_금액을_계산_할_수_있다(Menu menu, int quantity, int expectedTotal) {
        int totalAmount = menu.calculateDiscountAmountsForPromotion(quantity);

        assertThat(totalAmount).isEqualTo(expectedTotal);
    }

    @ParameterizedTest
    @MethodSource("메뉴_카테고리_소속여부")
    void 메뉴가_주어진_카테고리에_속하는지_알_수_있다(Menu menu, Category category, boolean expectedResult) {
        boolean actualResult = menu.belongsTo(category);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("메뉴_음료_여부")
    void 메뉴가_음료_카테고리에_속하는지_테스트(Menu menu, boolean isBeverage) {
        boolean result = menu.isBeverage();

        assertThat(result).isEqualTo(isBeverage);
    }

    @Test
    void 서로_같은_값을_가지는_메뉴는_같은_객체이다() {
        Menu actualMenu = Menu.from("양송이수프");
        Menu expectedMenu = Menu.from("양송이수프");

        assertThat(actualMenu).isSameAs(expectedMenu);
    }

    @Test
    void 서로_다른_값을_가지는_메뉴는_다른_객체이다() {
        Menu actualMenu = Menu.from("양송이수프");
        Menu expectedMenu = Menu.from("타파스");

        assertThat(actualMenu).isNotSameAs(expectedMenu);
    }

    @Test
    void 서로_같은_값을_가지는_메뉴는_해시코드가_같다() {
        Menu actualMenu = Menu.from("양송이수프");
        Menu expectedMenu = Menu.from("양송이수프");

        assertThat(actualMenu).hasSameHashCodeAs(expectedMenu);
    }

    private static Stream<Arguments> 메뉴_음료_여부() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, false),
                Arguments.of(Menu.TAPAS, false),
                Arguments.of(Menu.RED_WINE, true),
                Arguments.of(Menu.CHAMPAGNE, true),
                Arguments.of(Menu.ICE_CREAM, false)
        );
    }

    private static Stream<Arguments> 메뉴_카테고리_소속여부() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, Category.APPETIZER, true),
                Arguments.of(Menu.T_BONE_STEAK, Category.MAIN, true),
                Arguments.of(Menu.ICE_CREAM, Category.DESSERT, true),
                Arguments.of(Menu.RED_WINE, Category.BEVERAGE, true),
                Arguments.of(Menu.CAESAR_SALAD, Category.MAIN, false)
        );
    }

    private static Stream<Arguments> 메뉴_수량_예상_할인금액() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, 2, 12_000),
                Arguments.of(Menu.BBQ_RIBS, 1, 54_000),
                Arguments.of(Menu.ICE_CREAM, 3, 15_000),
                Arguments.of(Menu.ZERO_COLA, 0, 0)
        );
    }

    private static Stream<Arguments> 메뉴_수량_예상_금액() {
        return Stream.of(
                Arguments.of(Menu.MUSHROOM_SOUP, Quantity.from(2), 12_000),
                Arguments.of(Menu.BBQ_RIBS, Quantity.from(1), 54_000),
                Arguments.of(Menu.ICE_CREAM, Quantity.from(3), 15_000)
        );
    }

    private static Stream<Arguments> 메뉴_이름_예상_메뉴() {
        return Stream.of(
                Arguments.of("양송이수프", Menu.MUSHROOM_SOUP),
                Arguments.of("타파스", Menu.TAPAS),
                Arguments.of("시저샐러드", Menu.CAESAR_SALAD),
                Arguments.of("티본스테이크", Menu.T_BONE_STEAK),
                Arguments.of("바비큐립", Menu.BBQ_RIBS),
                Arguments.of("해산물파스타", Menu.SEAFOOD_PASTA),
                Arguments.of("크리스마스파스타", Menu.CHRISTMAS_PASTA),
                Arguments.of("초코케이크", Menu.CHOCOLATE_CAKE),
                Arguments.of("아이스크림", Menu.ICE_CREAM),
                Arguments.of("제로콜라", Menu.ZERO_COLA),
                Arguments.of("레드와인", Menu.RED_WINE),
                Arguments.of("샴페인", Menu.CHAMPAGNE),
                Arguments.of("없음", Menu.NONE)
        );
    }
}
