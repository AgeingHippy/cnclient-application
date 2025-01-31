package com.ageinghippy.cnclient_application.controller;

import com.ageinghippy.cnclient_application.service.ItemService;
import com.ageinghippy.cnclient_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ItemService itemService;
    private final UserService userService;

    @GetMapping(value = {"/","/homepage"})
    public String displayHomepage() {
        return "/homepage";
    }

    @GetMapping("/items")
    public String displayItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "item-list";
    }

}
