package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.TestExecutionDto;
import ru.moscow.tms.tms.models.*;
import ru.moscow.tms.tms.repository.*;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExecutionServiceImpl {
    final private ExecutionRepository executionRepository;
    final private ExecutionStatusRepository executionStatusRepository;
    final private CaseRepository caseRepository;
    final private RunRepository runRepository;
    final private UserRepository userRepository;

    @Transactional
    public void createTestExecution(
            TestExecutionDto dto, String authorName
    ) {
        UserEntity user = userRepository.findByUsername(authorName).orElseThrow(()-> new IllegalStateException("Author was not found"));
        TExecutionStatus status = executionStatusRepository.findByName(dto.getStatus()).orElseThrow(()-> new IllegalStateException("Status was not found"));
        TCase testCase = caseRepository.findByName(dto.getTestCase()).orElseThrow(()-> new IllegalStateException("Test Case was not found"));
        TRun testRun = runRepository.findByName(dto.getTestRun()).orElseThrow(()-> new IllegalStateException("Test Run was not found"));
        TExecution execution = new TExecution();
        execution.setName(dto.getName());
        execution.setDescription(dto.getDescription());
        execution.setCreation_date(new Date(System.currentTimeMillis()));
        execution.setStatus(status);
        execution.setTestCase(testCase);
        execution.setTestRun(testRun);
        execution.setTester(user);
        executionRepository.save(execution);
    }
}
