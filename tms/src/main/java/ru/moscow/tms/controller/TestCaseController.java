package ru.moscow.tms.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RequestBody;
import ru.moscow.tms.controller.dto.TestCaseDto;
import ru.moscow.tms.controller.dto.TestCaseResponseDto;

public class TestCaseController {

    // curl -d '{"user":"user", "password": "password"}' -H "Content-Type: application/json" -X POST http://localhost:8080/login


    public String getTestCases(String planId) {
        return "test cases";
    }

    public String createTestCase(@RequestBody TestCaseDto testCase) {
        return "test cases";
    }

    public String updateTestCase(@RequestBody TestCaseResponseDto planId) {
        return "test cases";
    }

    public String deleteTestCase(@PathParam("id") String caseId) {
        return "test cases";
    }
}
