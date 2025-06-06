package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseUpdateDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanUpdateDto;
import ru.moscow.tms.tms.models.*;
import ru.moscow.tms.tms.repository.CaseRepository;
import ru.moscow.tms.tms.repository.PlanRepository;
import ru.moscow.tms.tms.repository.PlanTypeRepository;
import ru.moscow.tms.tms.repository.PlanWithCasesRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements DeletableEntitiesMarker {
    final private PlanTypeRepository typeRepository;
    final private PlanWithCasesRepository planWithCasesrepository;
    final private PlanRepository planRepository;
    final private UserRepository userRepository;
    final private CaseRepository casesRepository;

    @Transactional
    public void createTestPlan(
            TestPlanDto dto, String authorName
    ) {
        if (planRepository.existsByName(dto.getName())) {
            throw new IllegalStateException("Plan with this name already exists");
        }
        UserEntity user = userRepository.findByUsername(authorName).orElseThrow(()-> new IllegalStateException("Author was not found"));
        TPlanType type = typeRepository.findByName(dto.getTypeName()).orElseThrow(()-> new IllegalStateException("Plan type was not found"));
        TPlan plan = new TPlan();
        plan.setPlanType(type);
        plan.set_active(true);
        plan.setCreationDate(new Date(System.currentTimeMillis()));
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setAuthor(user);
        planRepository.save(plan);
    }

    public List<TestCaseResponseDto> getAllCasesInPlan(Long planId) {
        TPlanWithCase plan = planWithCasesrepository.getReferenceById(planId);
        return plan.getCases().stream().filter(c -> !c.isDeleted()).map( (item) -> TestCaseResponseDto
                .builder()
                        .id(item.getId())
                        .plan(plan.getName())
                        .name(item.getName())
                        .description(item.getDescription())
                        .priority(item.getPriority().getName())
                        .category(item.getCategory().getName())
                        .status(item.getStatus().getName())
                        .author(item.getAuthor().getUsername())
                        .requirements(item.getRequirements())
                        .build()
                ).toList();
    }

    public void update(TestPlanUpdateDto dto, String username) {
        TPlan byId = planRepository.findById(dto.getId()).orElseThrow(() -> new IllegalStateException("Plan with this id does not exists"));
        TPlan withThisName = planRepository.findByName(dto.getName()).orElse(byId);
        if (Objects.equals(byId.getId(), withThisName.getId())) {
            TPlanType type = typeRepository.findByName(dto.getTypeName()).orElseThrow(()-> new IllegalStateException("Plan type was not found"));
            byId.setName(dto.getName());
            byId.setDescription(dto.getDescription());
            byId.setPlanType(type);
            planRepository.save(byId);
        } else {
            throw new IllegalStateException("Another plan with this name already exists");
        }
    }

    @Override
    public void markAsDeleted(Long id) {
        TPlan entity = planRepository.findById(id).orElseThrow(() -> new IllegalStateException("Plan with this id does not exists"));
        entity.setDeleted(true);
        planRepository.save(entity);
    }

    @Override
    public void unmarkAsDeleted(Long id) {
        TPlan entity = planRepository.findById(id).orElseThrow(() -> new IllegalStateException("Execution with this id does not exists"));
        entity.setDeleted(true);
        planRepository.save(entity);
    }

    public Page<TPlan> getPlans(int page, int size) {
        return planRepository.findByIsDeletedFalse(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationDate")));
    }

    public List<TPlanCalculation> getPlansCasesCount(int page, int size) {
        return planRepository.getPlanCasesCount(size, page*size);
    }

    public List<TPlanProcedure> getPlanProcedure(int year) {
        return planRepository.findTestCaseAfterYear(year);
    }

    public TestPlanResponseDto getPlanById(long id) {
        TPlan p = planRepository.findById(id).get();
        TestPlanResponseDto plan = new TestPlanResponseDto();
        plan.setId(p.getId());
        plan.setName(p.getName());
        plan.setDescription(p.getDescription());
        plan.setTypeName(p.getPlanType().getName());
        return plan;
    }

    public TestCaseUpdateDto getCaseById(long id) {
        TCase c =  casesRepository.findById(id).get();
        TestCaseUpdateDto testCase = new TestCaseUpdateDto();
        testCase.setName(c.getName());
        testCase.setDescription(c.getDescription());
        testCase.setCategory(c.getCategory().getName());
        testCase.setPriority(c.getPriority().getName());
        testCase.setId(c.getId());
        testCase.setStatus(c.getStatus().getName());
        return testCase;
    }
}
