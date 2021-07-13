package ru.mikhailkuleshov.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailkuleshov.springboot.model.Role;
import ru.mikhailkuleshov.springboot.model.User;
import ru.mikhailkuleshov.springboot.repository.RoleRepository;
import ru.mikhailkuleshov.springboot.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class MyUserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;



    public MyUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);


        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

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
        TypedQuery<Role> query = entityManager.createQuery(
                "select role  from Role role where role.id = :id", Role.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public void updateUser(Long id, User newUser) {
        entityManager.merge(newUser);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

}
