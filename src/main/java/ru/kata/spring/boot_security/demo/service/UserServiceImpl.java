package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> userOptional =  userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional =  userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(passwordCoder(user));
    }


    @Transactional
    public void update(User user) {
        //Берем старого юзера по id. Юзера, данные которого обновляются, кладем в переменную (по айди)
        User userDB = findById(user.getId());
        /*
        Сравниваем пароли пришедший в параметре и какой был у старого юзера,
        если не равны и у нового не пустой, то устанавливаем новый, иначе сетим старый
         */
        if (!(passwordEncoder.matches(user.getPassword(), userDB.getPassword()))
                && (user.getPassword() != null)
                && !(user.getPassword().equals(""))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDB.getPassword());
        }
        /*
        Теперь сравниваем роли. Если новая роль отличается от старой и не равна null,
        то устанавливаем новую роль, в противном случае оставляем старую.
         */
        if (userDB.getRoles() != user.getRoles()
                && (user.getRoles() != null)) {
            user.setRoles(user.getRoles());;
        } else {
            user.setRoles(userDB.getRoles());
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }


    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }


}
