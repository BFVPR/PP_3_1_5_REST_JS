package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;

    }


    @GetMapping
    public String user(Model model) {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userDetailsServiceImpl.loadUserByUsername(principalName);
        model.addAttribute("user", user);
        return "singleuser";
    }
}
