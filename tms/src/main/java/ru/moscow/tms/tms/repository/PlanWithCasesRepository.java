package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TPlanWithCase;

import java.util.List;
import java.util.Optional;

public interface PlanWithCasesRepository extends JpaRepository<TPlanWithCase, Long> {

    Optional<TPlanWithCase> findByName(String name);

    // Custom query
    @Query("SELECT b FROM TPlanWithCase b WHERE b.name like :query")
    List<TPlanWithCase> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);

}
