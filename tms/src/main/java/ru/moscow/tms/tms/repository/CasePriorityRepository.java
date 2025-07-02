package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.tms.models.TCasePriority;

import java.util.Optional;

public interface CasePriorityRepository extends JpaRepository<TCasePriority, Long> {

    Optional<TCasePriority> findByName(String name);

}
