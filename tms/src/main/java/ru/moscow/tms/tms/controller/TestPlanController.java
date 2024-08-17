package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.tms.controller.dto.CustomPage;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanUpdateDto;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.service.PlanServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/test/plan")
@RequiredArgsConstructor
public class TestPlanController {

    final private PlanServiceImpl service;

    @GetMapping("/{planId}/allCases")
    @ResponseStatus(HttpStatus.OK)
    public List<TestCaseResponseDto> getAllCasesInPlan(@PathVariable("planId") String planId) {
        return service.getAllCasesInPlan(Long.parseLong(planId));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomPage<TestPlanResponseDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<TPlan> pageResult = service.getPlans(page, size);
        return new CustomPage<>(pageResult.getNumber(), pageResult.getSize(), pageResult.hasNext(), pageResult.get().map(item -> new TestPlanResponseDto(item.getId(), item.getName(), item.getDescription(), item.getPlanType().getName(), getUserNameIfExists(item.getAuthor()), item.getCreationDate())).toList());
    }

    private String getUserNameIfExists(UserEntity author) {
        if(author == null) {
            return "no author";
        } else {
            return author.getUsername();
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestPlan(@RequestBody TestPlanDto planDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestPlan(planDto, userDetails.getUsername());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateTestPlan(@RequestBody TestPlanUpdateDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        service.update(dto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void markAsDeletedTestPlan(@PathVariable("id") String caseId) {
        service.markAsDeleted(Long.parseLong(caseId));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void unmarkAsDeletedTestPlan(@PathVariable("id") String caseId) {
        service.unmarkAsDeleted(Long.parseLong(caseId));
    }

}
