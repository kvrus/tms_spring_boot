package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.TestPlanDto;
import ru.moscow.tms.tms.service.PlanServiceImpl;

@RestController
@RequestMapping("/api/test/plan")
@RequiredArgsConstructor
public class TestPlanController {

    final private PlanServiceImpl service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Test Plan hello";
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestPlan(@RequestBody TestPlanDto planDto) {
        service.createTestPlan();
    }

}
