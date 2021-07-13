package ru.mikhailkuleshov.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mikhailkuleshov.springboot.model.User;



public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);
}
