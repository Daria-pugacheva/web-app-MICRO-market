package ru.gb.pugacheva.webapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.dtos.ProductDto;
import ru.gb.pugacheva.webapp.exceptions.DataValidationException;
import ru.gb.pugacheva.webapp.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.model.Category;
import ru.gb.pugacheva.webapp.model.Product;
import ru.gb.pugacheva.webapp.services.CategoryService;
import ru.gb.pugacheva.webapp.services.ProductService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping()
    public Page<ProductDto> findAll(@RequestParam (defaultValue = "1", name="p") int pageIndex,
                                    @RequestParam MultiValueMap <String,String> params){
        if(pageIndex<1){
            pageIndex=1;
        }
        return productService.findAll(pageIndex-1,10, params).map(ProductDto::new);
    }
//    //Базовый вариант, когда возвращали все продукты, а не страницу
//    @GetMapping("/products")
//    public List<ProductDto> findAll(){
//        return productService.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
//    }

//    //вариант старый + с объемным вариантом перехвата исключения
//    @GetMapping("/{id}")
//    public ResponseEntity <?> findById(@PathVariable Long id){
////        return new ProductDto (productService.findByID(id).get());
//        Optional <Product> product = productService.findByID(id);
//        if(!product.isPresent()){
//            return new ResponseEntity<>(new MarketError("Product id= " + id + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(new ProductDto (product.get()), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ProductDto findById (@PathVariable Long id){
        Product product = productService.findByID(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product id= " + id + " not found"));
        return new ProductDto(product);
    }

//    @PostMapping
//    public ProductDto save(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new DataValidationException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
//        }
//        Product product = new Product();
//        product.setPrice(productDto.getPrice());
//        product.setTitle(productDto.getTitle());
//        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category title = " + productDto.getCategoryTitle() + " not found"));
//        product.setCategory(category);
//        productService.save(product);
//        return new ProductDto(product);
//    }


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
        return new ProductDto(product);
    }

    @PutMapping()
    public void updateProduct (@RequestBody ProductDto productDto){
        productService.updateProductFromDto(productDto);
    }


}
