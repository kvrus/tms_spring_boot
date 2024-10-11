package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.tms.models.TCaseCategory;

import java.util.Optional;

public interface CaseCategoryRepository extends JpaRepository<TCaseCategory, Long> {

    Optional<TCaseCategory> findByName(String name);

}
