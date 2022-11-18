package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
//@RequestMapping(value = "/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String showLoginPage() {
        return "loginPage";
    }

//    @GetMapping( value = "/user")
//    public String showUserPage(Model model) {
//        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = (User) userDetailsServiceImpl.loadUserByUsername(principalName);
//        model.addAttribute("user", user);
//        return "userPage";
//    }

    @GetMapping( value = "/user")
    public String showUserPage(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "userPage";
    }

}
