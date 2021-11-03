package ru.gb.pugacheva.webapp.cart.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.pugacheva.webapp.api.dtos.ProductDto;


@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    private final WebClient productServiceWebClient;

    public ProductDto getProductById (Long productId) {
       ProductDto product = productServiceWebClient.get()
               .uri("/api/v1/products/"+ productId)
               .retrieve()
               .bodyToMono(ProductDto.class)
               .block();
       return product;
    }

}


////Старый вариант с RestTemplate
//    private final RestTemplate restTemplate;
//
//    @Value("${integration.product-service.url}")
//    private String productServiceUrl;
//
//    public ProductDto getProductById (Long productId) {
//        return restTemplate.getForObject(productServiceUrl +"/api/v1/products/" + productId, ProductDto.class);
//
//    }
