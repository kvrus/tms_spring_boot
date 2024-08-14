package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.TestPlanDto;
import ru.moscow.tms.tms.models.*;
import ru.moscow.tms.tms.repository.*;
import ru.moscow.tms.tms.repository.interfaces.CaseWithPlanRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl {
    final private CaseWithPlanRepository repository;
    final private PlanRepository planRepository;
    final private UserRepository userRepository;
    final private CasePriorityRepository priorityRepository;
    final private CaseStatusRepository statusRepository;
    final private CaseCategoryRepository categoryRepository;
    @Transactional
    public void createTestCase(
            TestCaseDto dto, String authorName
    ) {
        if (repository.existsByName(dto.getName())) {
            throw new IllegalStateException("Case with this name already exists");
        }
        UserEntity user = userRepository.findByUsername(authorName).orElseThrow(()-> new IllegalStateException("Author was not found"));
        TCasePriority priority = priorityRepository.findByName(dto.getPriority()).orElseThrow(()-> new IllegalStateException("Priority was not found"));
        TCaseCategory category = categoryRepository.findByName(dto.getCategory()).orElseThrow(()-> new IllegalStateException("Category was not found"));
        TCaseStatus status = statusRepository.findByName("in review").orElseThrow(()-> new IllegalStateException("Status was not found"));
        TPlan plan = planRepository.findByName(dto.getPlan()).orElseThrow(()-> new IllegalStateException("Plan was not found"));
        TCaseWithPlan testCase = new TCaseWithPlan();
        testCase.set_automated(false);
        testCase.setAuthor(user);
        testCase.setName(dto.getName());
        testCase.setCreation_date(new Date(System.currentTimeMillis()));
        testCase.setCategory(category);
        testCase.setPriority(priority);
        testCase.setStatus(status);
        testCase.setDescription(dto.getDescription());
        testCase.setPlans(List.of(plan));
        repository.save(testCase);
    }
}
