package ru.moscow.tms.tms.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.TestPlanDto;
import ru.moscow.tms.tms.service.CaseServiceImpl;

@RestController
@RequestMapping("/api/test/case")
@RequiredArgsConstructor
public class TestCaseController {

    final private CaseServiceImpl service;

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateExceptions(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String getTestCases(String planId) {
        return "test cases";
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestPlan(@RequestBody TestCaseDto caseDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestCase(caseDto, userDetails.getUsername());
    }

    public String updateTestCase(@RequestBody TestCaseResponseDto planId) {
        return "test cases";
    }

    public String deleteTestCase(@PathParam("id") String caseId) {
        return "test cases";
    }
}
