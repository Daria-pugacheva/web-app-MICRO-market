package ru.gb.pugacheva.webapp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.gb.pugacheva.webapp.model.Order;
import ru.gb.pugacheva.webapp.model.OrderItem;
import ru.gb.pugacheva.webapp.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor

public class OrderDto {
    private Long id;
    private List <OrderItemDto> items;
    private String address;
    private String phone;
    private int price;

    public OrderDto (Order order){
        this.id = order.getId();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.address = order.getAddress();
        this.phone = order.getPhone();
        this.price = order.getPrice();
    }


}
