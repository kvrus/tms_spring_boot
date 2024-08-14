package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.TestRunDto;
import ru.moscow.tms.tms.models.*;
import ru.moscow.tms.tms.repository.*;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TestRunServiceImpl {
    final private RunRepository runRepository;
    final private PlanRepository planRepository;
    final private UserRepository userRepository;

    @Transactional
    public void createTestRun(
            TestRunDto dto, String authorName
    ) {
        UserEntity user = userRepository.findByUsername(authorName).orElseThrow(()-> new IllegalStateException("Author was not found"));
        TPlan testPlan = planRepository.findByName(dto.getPlan()).orElseThrow(()-> new IllegalStateException("Test plan was not found"));
        TRun testRun = new TRun();
        testRun.setName(dto.getName());
        testRun.setDescription(dto.getDescription());
        testRun.setCreation_date(new Date(System.currentTimeMillis()));
        testRun.setPlan(testPlan);
        testRun.setAuthor(user);
        runRepository.save(testRun);
    }
}
