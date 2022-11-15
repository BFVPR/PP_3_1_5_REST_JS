package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User findById(int id);

    public void save(User user);

    public void update(int id, User user);

    public void deleteById(int id);

    public User passwordCoder(User user);

}
