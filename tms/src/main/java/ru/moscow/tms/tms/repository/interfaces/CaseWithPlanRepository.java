package ru.moscow.tms.tms.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TCase;
import ru.moscow.tms.tms.models.TCaseWithPlan;
import ru.moscow.tms.tms.models.TPlan;

import java.util.List;
import java.util.Optional;

public interface CaseWithPlanRepository extends JpaRepository<TCaseWithPlan, Long> {

    Optional<TCaseWithPlan> findByName(String name);

    @Query("SELECT b FROM TCaseWithPlan b WHERE b.name like :query")
    List<TCaseWithPlan> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);
}