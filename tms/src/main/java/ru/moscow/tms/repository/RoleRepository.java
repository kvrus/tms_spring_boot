package ru.moscow.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
