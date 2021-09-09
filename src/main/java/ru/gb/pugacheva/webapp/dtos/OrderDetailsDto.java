package ru.gb.pugacheva.webapp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.pugacheva.webapp.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String phone;
    private String address;

}
