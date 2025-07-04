package ru.moscow.tms.tms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseUpdateDto;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanDto;
import ru.moscow.tms.tms.service.CaseServiceImpl;

@RestController
@RequestMapping("/api/test/case")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080")
@Validated
public class TestCaseController {

    final private CaseServiceImpl service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestCase(@Valid @RequestBody TestCaseDto caseDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestCase(caseDto, userDetails.getUsername());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateTestCase(@Valid @RequestBody TestCaseUpdateDto caseDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.updateTestCase(caseDto, userDetails.getUsername());
    }

    @DeleteMapping("/{caseId}")
    @ResponseStatus(HttpStatus.OK)
    public void markAsDeletedTestCase(@PathVariable("caseId") String caseId) {
        service.markAsDeleted(Long.parseLong(caseId));
    }

    @GetMapping("/{caseId}")
    @ResponseStatus(HttpStatus.OK)
    public void unmarkAsDeletedTestCase(@PathVariable("caseId") String caseId) {
        service.unmarkAsDeleted(Long.parseLong(caseId));
    }
}
