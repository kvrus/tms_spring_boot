package ru.moscow.tms.tms.controller.dto.execution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestExecutionUpdateDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String testCase;
    private String testRun;
}
