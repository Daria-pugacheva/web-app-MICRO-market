package ru.gb.pugacheva.webapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.dtos.StringResponse;
import ru.gb.pugacheva.webapp.services.CartService;
import ru.gb.pugacheva.webapp.utils.Cart;

import java.security.Principal;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/generate")
    public StringResponse generateCartUuid(){
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCarts(Principal principal,@PathVariable String uuid){
        //TODO От Александра: продумать, вдруг кто-то вызовет это  без токена
        cartService.merge(principal,uuid);
    }


    @GetMapping("/{uuid}")
    public Cart getCartForCurrentUser(Principal principal, @PathVariable String uuid){
        return cartService.getCartForCurrentUser(principal, uuid);
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void addToCart (Principal principal, @PathVariable String uuid, @PathVariable Long productId){
        cartService.addItem (principal, uuid, productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrementItem (Principal principal, @PathVariable String uuid, @PathVariable Long productId){
        cartService.decrementItem (principal, uuid, productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void removetItem (Principal principal, @PathVariable String uuid, @PathVariable Long productId){
        cartService.removeItem(principal, uuid, productId);
    }

}
