package ru.gb.pugacheva.webapp.core.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.pugacheva.webapp.api.dtos.CategoryDto;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.core.model.Category;
import ru.gb.pugacheva.webapp.core.services.CategoryService;
import ru.gb.pugacheva.webapp.core.utils.Converter;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final Converter converter;

    @GetMapping("/{id}")
    public CategoryDto findById (@PathVariable Long id){
        Category c = categoryService.findByIdWithProducts(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category id " + id + " not found"));
        return converter.categoryToDto(c);
    }

}
