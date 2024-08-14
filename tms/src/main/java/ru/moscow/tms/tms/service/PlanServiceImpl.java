package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.TestPlanDto;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.models.TPlanType;
import ru.moscow.tms.tms.models.TPlanWithCase;
import ru.moscow.tms.tms.repository.PlanRepository;
import ru.moscow.tms.tms.repository.PlanTypeRepository;
import ru.moscow.tms.tms.repository.PlanWithCasesRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl {
    final private PlanTypeRepository typeRepository;
    final private PlanWithCasesRepository planWithCasesrepository;
    final private PlanRepository planRepository;
    final private UserRepository userRepository;

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
        plan.setCreation_date(new Date(System.currentTimeMillis()));
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setAuthor(user);
        planRepository.save(plan);
    }

    public List<TestCaseResponseDto> getAllCasesInPlan(Long planId) {
        TPlanWithCase plan = planWithCasesrepository.getReferenceById(planId);
        return plan.getCases().stream().map( (item) -> TestCaseResponseDto
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
}
