package ru.gb.pugacheva.webapp.dtos;

import lombok.Data;
import ru.gb.pugacheva.webapp.model.Product;

@Data
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto(Product product) {
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = product.getPrice();
    }

    public void changeQuantity(int delta){
        quantity += delta;
        if (quantity < 0){
            quantity = 0;
        }
        price = pricePerProduct * quantity;
    }
}
