package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseUpdateDto;
import ru.moscow.tms.tms.models.*;
import ru.moscow.tms.tms.repository.*;
import ru.moscow.tms.tms.repository.interfaces.CaseWithPlanRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CaseServiceImpl implements DeletableEntitiesMarker {
    final private CaseRepository caseRepository;
    final private CaseWithPlanRepository repository;
    final private PlanRepository planRepository;
    final private UserRepository userRepository;
    final private CasePriorityRepository priorityRepository;
    final private CaseStatusRepository statusRepository;
    final private CaseCategoryRepository categoryRepository;

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


    public void updateTestCase(TestCaseUpdateDto caseDto, String username) {
        TCaseWithPlan testCase = repository.findById(caseDto.getId()).orElseThrow(() -> new IllegalStateException("Case with this id does not exists"));
        TCaseWithPlan testCaseWithThisName = repository.findByName(caseDto.getName()).orElse(testCase);
        if(Objects.equals(testCase.getId(), testCaseWithThisName.getId())) {
            TCasePriority priority = priorityRepository.findByName(caseDto.getPriority()).orElseThrow(()-> new IllegalStateException("Priority was not found"));
            TCaseCategory category = categoryRepository.findByName(caseDto.getCategory()).orElseThrow(()-> new IllegalStateException("Category was not found"));
            TCaseStatus status = statusRepository.findByName(caseDto.getStatus()).orElseThrow(()-> new IllegalStateException("Status was not found"));
            testCase.setName(caseDto.getName());
            testCase.setDescription(caseDto.getDescription());
            testCase.setCategory(category);
            testCase.setPriority(priority);
            testCase.setStatus(status);
            repository.save(testCase);
        } else {
            throw new IllegalStateException("Another case with this name already exists");
        }
    }

    @Override
    public void markAsDeleted(Long id) {
        TCaseWithPlan testCase = repository.findById(id).orElseThrow(() -> new IllegalStateException("Case with this id does not exists"));
        testCase.setDeleted(true);
        repository.save(testCase);
    }

    @Override
    public void unmarkAsDeleted(Long id) {
        TCaseWithPlan testCase = repository.findById(id).orElseThrow(() -> new IllegalStateException("Case with this id does not exists"));
        testCase.setDeleted(false);
        repository.save(testCase);
    }

    public Page<TCase> getCases(int page, int size) {
        return caseRepository.findAll(PageRequest.of(page, size));
    }
}
