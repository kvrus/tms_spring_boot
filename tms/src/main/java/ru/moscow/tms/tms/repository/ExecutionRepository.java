package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TExecution;
import java.util.List;
import java.util.Optional;

public interface ExecutionRepository extends JpaRepository<TExecution, Long> {

    Optional<TExecution> findByName(String name);

    // Custom query
    @Query("SELECT b FROM TExecution b WHERE b.name like :query")
    List<TExecution> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);

}
