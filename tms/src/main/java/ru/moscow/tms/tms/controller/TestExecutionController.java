package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.TestExecutionDto;
import ru.moscow.tms.tms.service.ExecutionServiceImpl;

@RestController
@RequestMapping("/api/test/execution")
@RequiredArgsConstructor
public class TestExecutionController {
    final private ExecutionServiceImpl service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestExecution(@RequestBody TestExecutionDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestExecution(dto, userDetails.getUsername());
    }
}
