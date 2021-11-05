package ru.gb.pugacheva.webapp.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.pugacheva.webapp.api.dtos.CartDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderDetailsDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderItemDto;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.core.integration.CartServiceIntegration;
import ru.gb.pugacheva.webapp.core.model.*;
import ru.gb.pugacheva.webapp.core.repositories.OrderRepository;
import ru.gb.pugacheva.webapp.core.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;
    private final Converter converter;


    @Transactional
    public Order createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto cart = cartServiceIntegration.getUserCartDto(username);
        Order order = new Order();
        order.setUsername(username);
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
        cartServiceIntegration.clearUserCart(username);
        return order;
    }

    public Optional <Order> findById (Long id){
        return  orderRepository.findById(id);
    }

    @Transactional
    public Optional <OrderDto> findDtoByIdAndUsername (Long id, String username) {
        return  orderRepository.findOneByIdAndUsername(id,username).map(o -> converter.orderToDto(o));
    }

    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }


}


