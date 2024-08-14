package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.models.TPlanType;
import ru.moscow.tms.tms.repository.PlanRepository;
import ru.moscow.tms.tms.repository.PlanTypeRepository;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl {
    final private PlanTypeRepository typeRepository;
    final private PlanRepository repository;
    final private UserRepository userRepository;
    @Transactional
    public void createTestPlan() {
        if(repository.existsByName("first test plan name")) {
            return;
        }
        UserEntity user = userRepository.getReferenceById(1L);
        TPlanType type = typeRepository.findByName("base").get();
        TPlan plan = new TPlan();
        plan.setPlanType(type);
        plan.set_active(true);
        plan.setName("first test plan name");
        plan.setDescription("first test plan description");
        plan.setAuthor(user);
        repository.save(plan);
    }
}
