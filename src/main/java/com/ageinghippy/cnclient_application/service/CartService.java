package com.ageinghippy.cnclient_application.service;

import com.ageinghippy.cnclient_application.dto.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CartService {

    @LoadBalanced
    private final RestTemplate restTemplate;

    private final ItemService itemService;

    public void addItem(Long userId, Long itemId) {
        try {
            ResponseEntity<Cart> response =
                    restTemplate.postForEntity(
                            "http://CN-CART-MICROSERVICE/cart/{userId}?item-id={itemId}",
                            null,
                            Cart.class,
                            Map.of("userId", userId, "itemId", itemId));
            if (!response.getStatusCode().is2xxSuccessful()) {
                //todo - determine a friendlier response
                throw new RuntimeException("Failed to add item to cart");
            }
        } catch (Exception e) {
            //todo - handle 4xx exceptions here
            throw (e);
        }
    }

    public void removeItem(Long userId, Long itemId) {
        try {
            restTemplate.delete(
                    "http://CN-CART-MICROSERVICE/cart/{userId}?item-id={itemId}",
                    Map.of("userId", userId, "itemId", itemId));
        } catch (Exception e) {
            //todo - handle 4xx exceptions here
            throw (e);
        }
    }

    public void removeCartItem(Long userId, Long cartItemId) {
        try {
            restTemplate.delete(
                    "http://CN-CART-MICROSERVICE/cart/{userId}/{cartItemId}",
                    Map.of("userId", userId, "cartItemId", cartItemId));
        } catch (Exception e) {
            //todo - handle 4xx exceptions here
            throw (e);
        }
    }

    public Cart getCart(Long userId) {
        Cart cart;
        ResponseEntity<Cart> response =
                restTemplate.getForEntity(
                        "http://CN-CART-MICROSERVICE/cart/{userId}",
                        Cart.class,
                        Map.of("userId", userId));
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            cart = response.getBody();
        } else {
            cart = new Cart();
        }
        cart = populateItemDetails(cart);

        return cart;
    }

    private Cart populateItemDetails(Cart cart) {
        if (cart.getItems() != null) {
            cart.getItems().forEach(cartItem -> cartItem.setItem(itemService.getItem(cartItem.getItemId())));
        }
        //todo - implement handling of no item found [remove item from list?]

        return cart;
    }
}
