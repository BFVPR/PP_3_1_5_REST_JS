package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


//    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
//        this.userServiceImpl = userServiceImpl;
//        this.roleServiceImpl = roleServiceImpl;
//    }


    @GetMapping()
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("users", userService.findById(id));
        return "singleuser";
    }


    @GetMapping("/new")
    public String newUserView(Model model) {
        model.addAttribute("users", new User());
        model.addAttribute("roleList", roleService.getRoleList());
        return "new";
    }

    @PostMapping()
    public String addUserAction(@ModelAttribute("users") User user) {
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roleList", roleService.getRoleList());
        return "updateuser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }



}


