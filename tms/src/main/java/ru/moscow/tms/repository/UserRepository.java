package ru.moscow.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import ru.moscow.tms.models.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
