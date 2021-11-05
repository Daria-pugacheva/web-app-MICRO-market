package ru.gb.pugacheva.webapp.core.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.pugacheva.webapp.api.dtos.CartDto;
import ru.gb.pugacheva.webapp.api.dtos.ProductDto;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public void clearUserCart (String username){
      cartServiceWebClient.get()
              .uri("/api/v1/cart/0/clear")
              .header("username", username)
              .retrieve()
              .toBodilessEntity()
              .block();
    }

    public CartDto getUserCartDto (String username) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }

}

////СТарый вариант с RestTemplate
//    private final RestTemplate restTemplate;
//
//    @Value("${integration.cart-service.url}")
//    private String cartServiceUrl;
//
//    public CartDto getUserCartDto (String username){
//        MultiValueMap <String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("username", username);
//        HttpEntity rqEntity = new HttpEntity(headers);
//        CartDto cart= restTemplate.exchange(cartServiceUrl, HttpMethod.GET, rqEntity, CartDto.class).getBody();
//        return cart;
//    }
//
//    public void clearUserCart (String username){
//        MultiValueMap <String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("username", username);
//        HttpEntity rqEntity = new HttpEntity(headers);
//        restTemplate.exchange(cartServiceUrl + "/clear", HttpMethod.GET, rqEntity, void.class);
