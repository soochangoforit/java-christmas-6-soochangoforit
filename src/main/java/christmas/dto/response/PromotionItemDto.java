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
        String itemName = promotionItem.getItem().getName();
        int itemQuantity = promotionItem.getQuantity();

        return new PromotionItemDto(itemName, itemQuantity);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
