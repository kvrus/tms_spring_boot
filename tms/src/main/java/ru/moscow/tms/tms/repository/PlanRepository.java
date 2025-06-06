package ru.moscow.tms.tms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.models.TPlanCalculation;
import ru.moscow.tms.tms.models.TPlanProcedure;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<TPlan, Long> {

    Optional<TPlan> findByName(String name);

    Page<TPlan> findByIsDeletedFalse(Pageable pageable);

    // Custom query
    @Query("SELECT b FROM TPlan b WHERE b.name like :query")
    List<TPlan> findByNameSimilarity(@Param("query") String query);

    boolean existsByName(String name);

    @Query(value = """
        SELECT
            tp.id AS planId,
            tp.name AS name,
            COUNT(test_plan_case.case_id) AS caseCount
        FROM (select test_plan.id, test_plan.name from test_plan limit ?1 offset ?2) as tp
        LEFT JOIN
            test_plan_case ON tp.id = test_plan_case.plan_id
        GROUP BY
            tp.id, tp.name
        ORDER BY
            tp.id
        """, nativeQuery = true)
    List<TPlanCalculation> getPlanCasesCount(int limit, int offset);


    @Query(value = """
        SELECT * FROM FIND_TEST_CASE_AFTER_YEAR(2024)
        """, nativeQuery = true)
    List<TPlanProcedure> findTestCaseAfterYear(Integer year);

}
