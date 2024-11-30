package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.models.TPlanCalculation;

import java.util.List;
import java.util.Optional;

public interface PlanCalculationRepository extends JpaRepository<TPlan, Long> {

    @Query(value = """
        SELECT
            tp.id AS planId,
            tp.name AS name,
            COUNT(test_plan_case.case_id) AS casecount
        FROM (select test_plan.id, test_plan.name from test_plan limit ?1 offset ?2) as tp
        LEFT JOIN
            test_plan_case ON tp.id = test_plan_case.plan_id
        GROUP BY
            tp.id, tp.name
        ORDER BY
            tp.id
        """, nativeQuery = true)
    List<TPlanCalculation> getPlanCasesCount(int limit, int offset);

}
