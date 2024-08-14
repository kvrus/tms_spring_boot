package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.TestCaseDto;
import ru.moscow.tms.tms.controller.dto.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.TestPlanDto;
import ru.moscow.tms.tms.service.PlanServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/test/plan")
@RequiredArgsConstructor
public class TestPlanController {

    final private PlanServiceImpl service;

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateExceptions(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Test Plan hello";
    }

    @GetMapping("/{planId}/allCases")
    @ResponseStatus(HttpStatus.OK)
    public List<TestCaseResponseDto> getAllCasesInPlan(@PathVariable("planId") String planId) {
        return service.getAllCasesInPlan(Long.parseLong(planId));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestPlan(@RequestBody TestPlanDto planDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestPlan(planDto, userDetails.getUsername());
    }

}
