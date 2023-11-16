package christmas.dto.response;

import christmas.model.OrderAmounts;

public class OrderAmountsDto {
    private final int amounts;

    private OrderAmountsDto(int amounts) {
        this.amounts = amounts;
    }

    public static OrderAmountsDto from(OrderAmounts orderAmounts) {
        int amounts = orderAmounts.getAmounts();
        
        return new OrderAmountsDto(amounts);
    }

    public int getAmounts() {
        return amounts;
    }
}
