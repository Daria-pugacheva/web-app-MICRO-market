package ru.gb.pugacheva.webapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.dtos.StringResponse;
import ru.gb.pugacheva.webapp.services.CartService;
import ru.gb.pugacheva.webapp.utils.Cart;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{uuid}")
    public Cart getCart(Principal principal, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCurrentCartUuid(principal, uuid));
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.addToCart(getCurrentCartUuid(principal, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(principal, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(principal, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(Principal principal, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(principal, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(Principal principal, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(principal, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(Principal principal, String uuid) {
        if (principal != null) {
            return cartService.getCartUuidFromSuffix(principal.getName());
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }


//    ////////////////////////////// СТАРЫЙ ВАРИАНТ
//    private final CartService cartService;
//
//    @GetMapping("/generate")
//    public StringResponse generateCartUuid(){
//        return new StringResponse(UUID.randomUUID().toString());
//    }
//
//    @GetMapping("/{uuid}/merge")
//    public void mergeCarts(Principal principal,@PathVariable String uuid){
//        //TODO От Александра: продумать, вдруг кто-то вызовет это  без токена
//        cartService.merge(principal,uuid);
//    }
//
//
//    @GetMapping("/{uuid}")
//    public Cart getCartForCurrentUser(Principal principal, @PathVariable String uuid){
//        return cartService.getCartForCurrentUser(principal, uuid);
//    }
//
//    @GetMapping("/{uuid}/add/{productId}")
//    public void addToCart (Principal principal, @PathVariable String uuid, @PathVariable Long productId){
//        cartService.addItem (principal, uuid, productId);
//    }
//
//    @GetMapping("/{uuid}/decrement/{productId}")
//    public void decrementItem (Principal principal, @PathVariable String uuid, @PathVariable Long productId){
//        cartService.decrementItem (principal, uuid, productId);
//    }
//
//    @GetMapping("/{uuid}/remove/{productId}")
//    public void removetItem (Principal principal, @PathVariable String uuid, @PathVariable Long productId){
//        cartService.removeItem(principal, uuid, productId);
//    }

}
