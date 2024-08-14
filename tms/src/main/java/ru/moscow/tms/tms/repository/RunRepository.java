package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TRun;

import java.util.List;
import java.util.Optional;

public interface RunRepository extends JpaRepository<TRun, Long> {

    Optional<TRun> findByName(String name);

    // Custom query
    @Query("SELECT b FROM TRun b WHERE b.name like :query")
    List<TRun> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);

}
