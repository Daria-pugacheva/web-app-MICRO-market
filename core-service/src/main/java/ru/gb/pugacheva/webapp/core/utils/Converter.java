package ru.gb.pugacheva.webapp.core.utils;

import org.springframework.stereotype.Component;
import ru.gb.pugacheva.webapp.api.dtos.CategoryDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderDto;
import ru.gb.pugacheva.webapp.api.dtos.OrderItemDto;
import ru.gb.pugacheva.webapp.api.dtos.ProductDto;
import ru.gb.pugacheva.webapp.core.model.Category;
import ru.gb.pugacheva.webapp.core.model.Order;
import ru.gb.pugacheva.webapp.core.model.OrderItem;
import ru.gb.pugacheva.webapp.core.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    public ProductDto productToDto (Product product){
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    public CategoryDto categoryToDto (Category category){
        List <ProductDto> products = category.getProducts().stream().map(p -> productToDto(p)).collect(Collectors.toList());
        return new CategoryDto(category.getId(), category.getTitle(),products);
    }

    public OrderItemDto orderItemToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getProduct().getId(),orderItem.getProduct().getTitle(),
                orderItem.getQuantity(),orderItem.getPricePerProduct(), orderItem.getPrice());
    }

    public OrderDto orderToDto (Order order){
        return new OrderDto(order.getId(),
                order.getItems().stream().map(oi -> orderItemToDto(oi)).collect(Collectors.toList()),
                order.getAddress(), order.getPhone(), order.getPrice(), order.getStatus());
    }

}
