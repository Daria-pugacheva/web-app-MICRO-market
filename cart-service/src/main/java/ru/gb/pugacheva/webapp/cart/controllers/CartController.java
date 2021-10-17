package ru.gb.pugacheva.webapp.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.api.dtos.CartDto;
import ru.gb.pugacheva.webapp.api.dtos.ProductDto;
import ru.gb.pugacheva.webapp.api.dtos.StringResponse;
import ru.gb.pugacheva.webapp.cart.integration.ProductServiceIntegration;
import ru.gb.pugacheva.webapp.cart.services.CartService;
import ru.gb.pugacheva.webapp.cart.utils.Cart;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    private final ProductServiceIntegration productServiceIntegration;

    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader (required = false) String username, @PathVariable String uuid) {
        Cart cart = cartService.getCurrentCart(getCurrentCartUuid(username, uuid));
        return new CartDto(cart.getItems(),cart.getTotalPrice());
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(@RequestHeader (required = false)String username, @PathVariable String uuid, @PathVariable Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        cartService.addToCart(getCurrentCartUuid(username, uuid), product);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader String username, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

}
