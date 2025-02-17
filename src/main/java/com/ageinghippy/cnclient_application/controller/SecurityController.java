package com.ageinghippy.cnclient_application.controller;

import com.ageinghippy.cnclient_application.dto.User;
import com.ageinghippy.cnclient_application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final UserService userService;

    @GetMapping("/register")
    public String displayRegister(Model model) {
        model.addAttribute("user",new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute User user, Model model) {
        String view;
        try {
            user = userService.createUser(user);
            view = "register-success";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            view = "register-failure";
        }

        return view;
    }

    @GetMapping("/register-success")
    public String displayRegisterSuccess(Model model, Authentication authentication) {
        model.addAttribute("user",authentication.getPrincipal());

        return "register-success";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/sign-out")
    public String signOut() {
        return "/logout";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
        // Get the Spring Authentication object of the current request.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // In case you are not filtering the users of this request url.
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication); // <= This is the call you are looking for.
        }
        return "redirect:/homepage";
    }

}
