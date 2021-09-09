package ru.gb.pugacheva.webapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.services.CartService;
import ru.gb.pugacheva.webapp.utils.Cart;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCartForCurrentUser(){
        return cartService.getCartForCurrentUser();
    }

    @GetMapping("/add/{productId}")
    public void addToCart (@PathVariable Long productId){
        cartService.addItem (productId);
    }

    @GetMapping("/decrement/{productId}")
    public void decrementItem (@PathVariable Long productId){
        cartService.decrementItem (productId);
    }

    @GetMapping("/remove/{productId}")
    public void removetItem (@PathVariable Long productId){
        cartService.removeItem(productId);
    }

}
