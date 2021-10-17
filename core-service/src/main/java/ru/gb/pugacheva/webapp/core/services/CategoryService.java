package ru.gb.pugacheva.webapp.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.pugacheva.webapp.core.model.Category;
import ru.gb.pugacheva.webapp.core.model.Product;
import ru.gb.pugacheva.webapp.core.repositories.CategoryRepository;
import ru.gb.pugacheva.webapp.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional <Category> findByTitle (String title){
        return categoryRepository.findByTitle(title);
    }

    public Optional <Category> findById (Long id){
        return categoryRepository.findById(id);
    }

    public Optional <Category> findByIdWithProducts (Long id){
        return categoryRepository.findByIdWithProducts(id);
    }
}
