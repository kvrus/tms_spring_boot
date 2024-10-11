package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.tms.models.TPlanType;

import java.util.Optional;

public interface PlanTypeRepository extends JpaRepository<TPlanType, Long> {

    Optional<TPlanType> findByName(String name);

}
