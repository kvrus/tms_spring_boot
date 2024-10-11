package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.tms.models.TCaseStatus;

import java.util.Optional;

public interface CaseStatusRepository extends JpaRepository<TCaseStatus, Long> {

    Optional<TCaseStatus> findByName(String name);

}
