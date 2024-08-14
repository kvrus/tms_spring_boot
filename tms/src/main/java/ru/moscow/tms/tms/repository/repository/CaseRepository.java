package ru.moscow.tms.tms.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TCase;
import java.util.List;

public interface CaseRepository extends JpaRepository<TCase, Long> {

    List<TCase> findByName(String name);

    // Custom query
    @Query("SELECT b FROM TCase b WHERE b.name like :query")
    List<TCase> findByNameSimilarity(@Param("query") String query);

}
