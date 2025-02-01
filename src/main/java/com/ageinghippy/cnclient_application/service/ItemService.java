package com.ageinghippy.cnclient_application.service;

import com.ageinghippy.cnclient_application.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ItemService {

    @LoadBalanced
    private final RestTemplate restTemplate;

    public List<Item> getAllItems() {
        ResponseEntity<Item[]> response =
                restTemplate.getForEntity(
                        "http://CN-ITEM-MICROSERVICE/item", Item[].class);
        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            return Arrays.asList(response.getBody());
        } else {
            return null;
        }
    }

    //todo - implement caching here
    public Item getItem(Long itemId) {
        ResponseEntity<Item> response =
                restTemplate.getForEntity(
                        "http://CN-ITEM-MICROSERVICE/item/{id}", Item.class, Map.of("id",itemId));
        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
