package ru.mikhailkuleshov.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mikhailkuleshov.springboot.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
