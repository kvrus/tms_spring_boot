package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.TestCaseUpdateDto;
import ru.moscow.tms.tms.service.CaseServiceImpl;

@RestController
@RequestMapping("/api/test/case")
@RequiredArgsConstructor
public class TestCaseController {

    final private CaseServiceImpl service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestCase(@RequestBody TestCaseDto caseDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestCase(caseDto, userDetails.getUsername());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateTestCase(@RequestBody TestCaseUpdateDto caseDto, @AuthenticationPrincipal UserDetails userDetails) {
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
