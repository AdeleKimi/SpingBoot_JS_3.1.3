package ru.mikhailkuleshov.springboot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mikhailkuleshov.springboot.model.Role;
import ru.mikhailkuleshov.springboot.model.User;
import ru.mikhailkuleshov.springboot.repository.RoleRepository;
import ru.mikhailkuleshov.springboot.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public interface UserService {

    public User loadUserByFirstName(String name);

    public User findUserById(Long userId);

    public List<Role> allRoles();

    public List<User> allUsers();

    public boolean saveUser(User user);

    public boolean deleteUser(Long userId);

    public void addRole(User user, Role role);


    public Role findRoleById(Long id);

    public void updateUser(Long id, User newUser);

    public Role findRoleByName(String name);


}
