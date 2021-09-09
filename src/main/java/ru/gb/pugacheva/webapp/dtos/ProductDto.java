package ru.gb.pugacheva.webapp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.gb.pugacheva.webapp.model.Product;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull(message = "Товар должен иметь название")
    @Length(min = 3, max=255,message = "Название должно содержать от 3 до 255 символов")
    private String title;
    @Min(value = 1,message = "Цена не может быть меньше 1 руб.")
    private int price;
    @NotNull(message = "Товар должен иметь категорию")
    private String categoryTitle;

    public ProductDto (Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
    }

}
