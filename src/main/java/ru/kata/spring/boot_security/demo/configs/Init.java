package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserServiceImpl userService;

    @Autowired
    public Init(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void addAdminInDB() {
        //Создаем пользователей при старте приложения

        Set<Role> roleAdmin = new HashSet<>();
        roleAdmin.add(new Role("ROLE_ADMIN"));
        Set<Role> roleUser = new HashSet<>();
        roleUser.add(new Role("ROLE_USER"));

        userService.save(new User("Bobby", "Tarantino", 20, "admin@mail.ru", "111", roleAdmin));
        userService.save(new User("Billy", "Bob", 16, "user@mail.ru", "111", roleUser));
    }
}
