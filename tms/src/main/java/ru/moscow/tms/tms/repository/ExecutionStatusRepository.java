package ru.moscow.tms.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moscow.tms.tms.models.TExecutionStatus;

import java.util.Optional;

public interface ExecutionStatusRepository extends JpaRepository<TExecutionStatus, Long> {

    Optional<TExecutionStatus> findByName(String name);

}
