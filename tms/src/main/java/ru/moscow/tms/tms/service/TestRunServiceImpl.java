package ru.moscow.tms.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.controller.dto.run.TestRunDto;
import ru.moscow.tms.tms.controller.dto.run.TestRunUpdateDto;
import ru.moscow.tms.tms.models.*;
import ru.moscow.tms.tms.repository.*;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TestRunServiceImpl implements DeletableEntitiesMarker {
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
        testRun.setCreationDate(new Date(System.currentTimeMillis()));
        testRun.setPlan(testPlan);
        testRun.setAuthor(user);
        runRepository.save(testRun);
    }

    public void update(TestRunUpdateDto dto, String username) {
        TRun byId = runRepository.findById(dto.getId()).orElseThrow(() -> new IllegalStateException("Plan with this id does not exists"));
        TRun withThisName = runRepository.findByName(dto.getName()).orElse(byId);
        if (Objects.equals(byId.getId(), withThisName.getId())) {
            byId.setName(dto.getName());
            byId.setDescription(dto.getDescription());
            runRepository.save(byId);
        } else {
            throw new IllegalStateException("Another run with this name already exists");
        }
    }

    @Override
    public void markAsDeleted(Long id) {
        TRun entity = runRepository.findById(id).orElseThrow(() -> new IllegalStateException("Run with this id does not exists"));
        entity.setDeleted(true);
        runRepository.save(entity);
    }

    @Override
    public void unmarkAsDeleted(Long id) {
        TRun entity = runRepository.findById(id).orElseThrow(() -> new IllegalStateException("Run with this id does not exists"));
        entity.setDeleted(true);
        runRepository.save(entity);
    }

    public Page<TRun> getRuns(int page, int size) {
        return runRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationDate")));
    }
}
