package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TPlan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<TPlan, Long> {

    Optional<TPlan> findByName(String name);

    // Custom query
    @Query("SELECT b FROM TPlan b WHERE b.name like :query")
    List<TPlan> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);

}
