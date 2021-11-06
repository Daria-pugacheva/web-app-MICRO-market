package ru.gb.pugacheva.webapp.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;;
import ru.gb.pugacheva.webapp.api.dtos.OrderDetailsDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderDto;
import ru.gb.pugacheva.webapp.api.dtos.StringResponse;
import ru.gb.pugacheva.webapp.core.services.OrderService;
import ru.gb.pugacheva.webapp.core.utils.Converter;

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
    public StringResponse createOrder (@RequestBody OrderDetailsDto orderDetailsDto, @RequestHeader String username){
       return new StringResponse(orderService.createOrder(username, orderDetailsDto).getId().toString());
    }

    @GetMapping
    public List<OrderDto> getOrdersForCurrentUser (@RequestHeader String username){
       return orderService.findAllByUsername(username).stream()
               .map(o -> converter.orderToDto(o)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getOrderForCurrentUser (@RequestHeader String username, @PathVariable Long id){
        return orderService.findDtoByIdAndUsername(id, username).get();
    }

    @GetMapping("/status/{id}")
    public void changeOrderStatus (@PathVariable Long id){
        orderService.setPaidOrderStatus(id);
    }

}
