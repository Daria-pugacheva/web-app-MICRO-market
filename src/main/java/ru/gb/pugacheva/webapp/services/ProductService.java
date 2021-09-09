package ru.gb.pugacheva.webapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.pugacheva.webapp.dtos.ProductDto;
import ru.gb.pugacheva.webapp.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.model.Category;
import ru.gb.pugacheva.webapp.model.Product;
import ru.gb.pugacheva.webapp.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    public Page<Product> findAll(int pageIndex, int pageSize){
        return productRepository.findAll(PageRequest.of(pageIndex,pageSize));
    }
//    //Базовый вариант, когда возвращали все товары сразу
//    public List<Product> findAll(){
//        return productRepository.findAll();
//    }

    public Optional<Product> findByID (Long id){
        return productRepository.findById(id);
    }

    public Product save (Product product){
        return productRepository.save(product);
    }

    @Transactional
    public void updateProductFromDto (ProductDto productDto) {
        Product product = findByID(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Product id= " + productDto.getId() + " not found"));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        if (!product.getCategory().getTitle().equals(productDto.getCategoryTitle())) {
            Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Category title= " + productDto.getCategoryTitle() + " not found"));
            product.setCategory(category);
        }
    }

}
