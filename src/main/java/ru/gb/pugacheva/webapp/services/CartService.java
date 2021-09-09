package ru.gb.pugacheva.webapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.pugacheva.webapp.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.model.Product;
import ru.gb.pugacheva.webapp.utils.Cart;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init(){
        this.cart = new Cart ();
    }

    public Cart getCartForCurrentUser(){
        return cart;
    }

    public void addItem(Long productId) {
        if (cart.add(productId)){
            return;
        }
        Product product = productService.findByID(productId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Невозможно добавить продукт в корзину, т.к. продукт с id " + productId + " не существует"));
        cart.add(product);
    }

    public void removeItem (Long productId){
        cart.remove(productId);
    }

    public void decrementItem (Long productId){
        cart.decrement(productId);
    }

    public void clearCart() {
        cart.clear();
    }
}
