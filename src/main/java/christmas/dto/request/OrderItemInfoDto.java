package christmas.dto.request;

public class OrderItemInfoDto {
    private final String menuName;
    private final int quantity;

    public OrderItemInfoDto(String menuName, int quantity) {
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
