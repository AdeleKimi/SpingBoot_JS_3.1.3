package ru.mikhailkuleshov.springboot.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mikhailkuleshov.springboot.model.User;
import ru.mikhailkuleshov.springboot.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByFirstName(name);


        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
