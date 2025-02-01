package com.ageinghippy.cnclient_application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CartItem {

    private Long id;
    private Long itemId;
    @JsonIgnore
    private Item item;
    private Integer amount;

}
