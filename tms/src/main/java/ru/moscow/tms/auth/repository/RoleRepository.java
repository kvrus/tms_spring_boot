package ru.moscow.tms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.auth.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
