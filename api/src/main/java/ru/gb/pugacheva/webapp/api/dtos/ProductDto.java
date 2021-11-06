package ru.gb.pugacheva.webapp.api.dtos;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String categoryTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}

//поля с валидацией
//    private Long id;
//    @NotNull(message = "Товар должен иметь название")
//    @Length(min = 3, max=255,message = "Название должно содержать от 3 до 255 символов")
//    private String title;
//    @Min(value = 1,message = "Цена не может быть меньше 1 руб.")
//    private int price;
//    @NotNull(message = "Товар должен иметь категорию")
//    private String categoryTitle;
