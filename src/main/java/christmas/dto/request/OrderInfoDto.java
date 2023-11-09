package christmas.dto.request;

public class OrderInfoDto {
    private final String menuName;
    private final int quantity;

    public OrderInfoDto(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getQuantity() {
        return quantity;
    }
}
