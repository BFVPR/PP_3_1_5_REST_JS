package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {

    private final UserServiceImpl userService;

    private final RoleService roleService;

    @Autowired
    public Init(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    Создаем админа и юзера при старте приложения
    @PostConstruct
    public void addRolesInDB() {

        if (userService.findByEmail("admin@mail.ru") == null) {
            if (roleService.getRoleByName("ROLE_ADMIN") == null) {
                Role roleAdmin = new Role("ROLE_ADMIN");
                roleService.save(roleAdmin);
            }
            Role roleAdmin = roleService.getRoleByName("ROLE_ADMIN");
            User admin = new User();
            admin.setFirstName("Bobby");
            admin.setLastName("Tarantino");
            admin.setEmail("admin@mail.ru");
            admin.setPassword("111");
            admin.setAge(20);
            admin.setRoles(Set.of(roleAdmin));
            userService.save(admin);
        }
        if (userService.findByEmail("user@mail.ru") == null) {
            if (roleService.getRoleByName("ROLE_USER") == null) {
                Role roleUser = new Role("ROLE_USER");
                roleService.save(roleUser);
            }
            Role roleUser = roleService.getRoleByName("ROLE_USER");
            User user = new User();
            user.setFirstName("Billy");
            user.setLastName("Bob");
            user.setEmail("user@mail.ru");
            user.setPassword("111");
            user.setAge(16);
            user.setRoles(Set.of(roleUser));
            userService.save(user);
        }

    }

}
