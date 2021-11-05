package ru.gb.pugacheva.webapp.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.api.dtos.ProductDto;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.core.exceptions.DataValidationException;
import ru.gb.pugacheva.webapp.core.model.Category;
import ru.gb.pugacheva.webapp.core.model.Product;
import ru.gb.pugacheva.webapp.core.services.CategoryService;
import ru.gb.pugacheva.webapp.core.services.ProductService;
import ru.gb.pugacheva.webapp.core.utils.Converter;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final Converter converter;

    @GetMapping()
    public Page<ProductDto> findAll(@RequestParam (defaultValue = "1", name="p") int pageIndex,
                                    @RequestParam MultiValueMap <String,String> params){
        if(pageIndex<1){
            pageIndex=1;
        }
        return productService.findAll(pageIndex-1,10, params).map(p -> converter.productToDto(p));
    }

    @GetMapping("/{id}")
    public ProductDto findById (@PathVariable Long id){
        Product product = productService.findByID(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product id= " + id + " not found"));
        return converter.productToDto(product);
    }

    @PostMapping()
    public ProductDto save (@RequestBody @Validated ProductDto productDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new DataValidationException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        }
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Category title= " + productDto.getCategoryTitle() + " not found"));
        product.setCategory(category);
        productService.save(product);
        return converter.productToDto(product);
    }

    @PutMapping()
    public void updateProduct (@RequestBody ProductDto productDto){
        productService.updateProductFromDto(productDto);
    }


}
