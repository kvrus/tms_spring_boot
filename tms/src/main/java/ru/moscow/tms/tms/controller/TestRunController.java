package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.tms.controller.dto.TestRunDto;
import ru.moscow.tms.tms.service.TestRunServiceImpl;

@RestController
@RequestMapping("/api/test/run")
@RequiredArgsConstructor
public class TestRunController {
    final private TestRunServiceImpl service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTestExecution(@RequestBody TestRunDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestRun(dto, userDetails.getUsername());
    }
}
