package ru.mikhailkuleshov.springboot.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailkuleshov.springboot.model.Role;
import ru.mikhailkuleshov.springboot.model.User;
import ru.mikhailkuleshov.springboot.repository.RoleRepository;
import ru.mikhailkuleshov.springboot.repository.UserRepository;

import java.util.*;


@Service
@Transactional
public class MyUserService {

    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    final
    PasswordEncoder passwordEncoder;


    public MyUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User loadUserByUsername(String name) {
        User user = userRepository.findByName(name);

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByName(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        addRole(user, findRoleByName("ROLE_USER"));

        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public void addRole(User user, Role role) {
        Set<Role> roleSet;

        if (user.getRoles() != null) {
            roleSet = user.getRoles();
        } else {
            roleSet = new HashSet<>();
        }
        roleSet.add(role);
        user.setRoles(roleSet);
    }


    public Role findRoleById(Long id) {
        Optional<Role> roleFromDb = roleRepository.findById(id);
        return roleFromDb.orElse(new Role());
    }

    public void updateUser(Long id, User newUser) {
        saveUser(newUser);

    }

    public Role findRoleByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }




}
