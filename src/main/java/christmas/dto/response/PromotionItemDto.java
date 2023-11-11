package christmas.dto.response;

import christmas.model.PromotionItem;

public class PromotionItemDto {
    private final String name;
    private final int quantity;

    private PromotionItemDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static PromotionItemDto from(PromotionItem promotionItem) {
        String name = promotionItem.getItem().getName();
        int quantity = promotionItem.getQuantity();

        return new PromotionItemDto(name, quantity);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
