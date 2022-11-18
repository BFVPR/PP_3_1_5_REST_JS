package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showAdminPage(ModelMap model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("rolesList", roleService.getRoleList());
        model.addAttribute("newUser", new User());
        return "adminPage";
    }


    @GetMapping("/addUser")
    public String saveUserView(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping(value = "/saveUser")
    public String saveUserAction(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping("editUser/{id}/")
    public String updateUserView(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.findById(id));
        return "update";
    }

    @PostMapping("/{id}")
    public String updateUserAction(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("deleteUser/{id}")
    public String deleteUserAction(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


}

