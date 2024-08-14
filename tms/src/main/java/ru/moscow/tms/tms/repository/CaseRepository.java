package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TCase;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.repository.interfaces.CaseWithPlanRepository;

import java.util.List;

public interface CaseRepository extends JpaRepository<TCase, Long> {

    List<TCase> findByName(String name);

    @Query("SELECT b FROM TPlan b WHERE b.name like :query")
    List<TPlan> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);
}
