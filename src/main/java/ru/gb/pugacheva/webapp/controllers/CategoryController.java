package ru.gb.pugacheva.webapp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.pugacheva.webapp.dtos.CategoryDto;
import ru.gb.pugacheva.webapp.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.model.Product;
import ru.gb.pugacheva.webapp.services.CategoryService;

import javax.persistence.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDto findById (@PathVariable Long id){
        return new CategoryDto(categoryService.findByIdWithProducts(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category id " + id + " not found")));
    }

}
