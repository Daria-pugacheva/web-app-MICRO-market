package ru.gb.pugacheva.webapp.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;;
import ru.gb.pugacheva.webapp.api.dtos.OrderDetailsDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderDto;
import ru.gb.pugacheva.webapp.core.services.OrderService;
import ru.gb.pugacheva.webapp.core.utils.Converter;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final Converter converter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder (@RequestBody OrderDetailsDto orderDetailsDto, Principal principal){
       orderService.createOrder(principal, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getOrdersForCurrentUser (Principal principal){
       return orderService.findAllByUsername(principal.getName()).stream()
               .map(o -> converter.orderToDto(o)).collect(Collectors.toList());
    }

}
