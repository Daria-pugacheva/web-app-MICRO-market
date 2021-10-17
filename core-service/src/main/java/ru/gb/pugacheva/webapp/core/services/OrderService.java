package ru.gb.pugacheva.webapp.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.pugacheva.webapp.api.dtos.CartDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderDetailsDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderItemDto;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.core.integration.CartServiceIntegration;
import ru.gb.pugacheva.webapp.core.model.*;
import ru.gb.pugacheva.webapp.core.repositories.OrderRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;


    @Transactional
    public void createOrder(Principal principal, OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException
                ("Не удалось найти в базе пользователя с именем " + principal.getName()));
        CartDto cart = cartServiceIntegration.getUserCartDto(principal);
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cart.getTotalPrice());
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto i : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(i.getPrice());
            orderItem.setPricePerProduct(i.getPricePerProduct());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setProduct(productService.findByID(i.getProductId()).orElseThrow(() -> new ResourceNotFoundException
                    ("Не удалось найти продукт ID продукта: " + i.getProductId())));
            items.add(orderItem);

        }
        order.setItems(items);
        orderRepository.save(order);
        cartServiceIntegration.clear(principal);
    }

    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }


}


