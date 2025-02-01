package com.ageinghippy.cnclient_application.controller;

import com.ageinghippy.cnclient_application.dto.Cart;
import com.ageinghippy.cnclient_application.dto.User;
import com.ageinghippy.cnclient_application.service.CartService;
import com.ageinghippy.cnclient_application.service.ItemService;
import com.ageinghippy.cnclient_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ItemService itemService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping(value = {"/","/homepage"})
    public String displayHomepage() {
        return "/homepage";
    }

    @GetMapping("/items")
    public String displayItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "item-list";
    }

    @GetMapping("/cart/add-item/{itemId}")
    public String addItemToCart(@PathVariable Long itemId, Authentication authentication) {
        Long userId = userService.getUser(authentication.getName()).getId();
        cartService.addItem(userId, itemId);

        return "redirect:/cart";
    }

    @GetMapping("/cart/remove-item/{itemId}")
    public String removeItemFromCart(@PathVariable Long itemId, Authentication authentication) {
        Long userId = userService.getUser(authentication.getName()).getId();
        cartService.removeItem(userId, itemId);

        return "redirect:/cart";
    }

    @GetMapping("/cart/remove-cart-item/{cartItemId}")
    public String removeCartItemFromCart(@PathVariable Long cartItemId, Authentication authentication) {
        Long userId = userService.getUser(authentication.getName()).getId();
        cartService.removeCartItem(userId, cartItemId);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String showCart(Authentication authentication, Model model) {
        Cart cart = cartService.getCart(userService.getUser(authentication.getName()).getId());

        model.addAttribute("items", cart.getItems());

        return "cart";
    }

}
