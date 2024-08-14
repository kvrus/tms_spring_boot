package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.TestPlanDto;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.models.TPlanType;
import ru.moscow.tms.tms.repository.PlanRepository;
import ru.moscow.tms.tms.repository.PlanTypeRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl {
    final private PlanTypeRepository typeRepository;
    final private PlanRepository repository;
    final private UserRepository userRepository;

    @Transactional
    public void createTestPlan(
            TestPlanDto dto, String authorName
    ) {
        if (repository.existsByName(dto.getName())) {
            throw new IllegalStateException("Plan with this name already exists");
        }
        UserEntity user = userRepository.findByUsername(authorName).orElseThrow(()-> new IllegalStateException("Author wan not found"));
        TPlanType type = typeRepository.findByName(dto.getTypeName()).orElseThrow(()-> new IllegalStateException("Plan type wan not found"));
        TPlan plan = new TPlan();
        plan.setPlanType(type);
        plan.set_active(true);
        plan.setCreation_date(new Date(System.currentTimeMillis()));
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setAuthor(user);
        repository.save(plan);
    }
}
