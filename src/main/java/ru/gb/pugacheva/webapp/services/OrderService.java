package ru.gb.pugacheva.webapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.pugacheva.webapp.dtos.OrderDetailsDto;
import ru.gb.pugacheva.webapp.dtos.OrderItemDto;
import ru.gb.pugacheva.webapp.dtos.ProductDto;
import ru.gb.pugacheva.webapp.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.model.*;
import ru.gb.pugacheva.webapp.repositories.OrderRepository;
import ru.gb.pugacheva.webapp.repositories.ProductRepository;
import ru.gb.pugacheva.webapp.utils.Cart;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;


    @Transactional
    public void createOrder(Principal principal, OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException
                ("Не удалось найти в базе пользователя с именем " + principal.getName()));
        Cart cart = cartService.getCartForCurrentUser(principal, null);
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
        cartService.clearCart(principal, null);
    }

    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }


}


