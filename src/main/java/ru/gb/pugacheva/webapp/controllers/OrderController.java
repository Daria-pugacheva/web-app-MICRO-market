package ru.gb.pugacheva.webapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.dtos.OrderDetailsDto;
import ru.gb.pugacheva.webapp.services.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder (@RequestBody OrderDetailsDto orderDetailsDto){
        cartService.clearCart();

    }

}
