package ru.moscow.tms.tms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TCase;
import ru.moscow.tms.tms.models.TPlan;

import java.util.List;
import java.util.Optional;

public interface CaseRepository extends JpaRepository<TCase, Long> {

    Optional<TCase> findByName(String name);

    @Query("SELECT b FROM TPlan b WHERE b.name like :query")
    List<TPlan> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);

    Page<TCase> findByCategoryId(Long category_id, Pageable pageable);
}
