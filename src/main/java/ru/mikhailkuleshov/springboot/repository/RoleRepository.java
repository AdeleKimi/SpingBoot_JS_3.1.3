package ru.mikhailkuleshov.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mikhailkuleshov.springboot.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(String name);
}
