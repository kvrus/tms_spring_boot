package ru.moscow.tms.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResponseDto {
    private Long caseId;
    private String name;
    private String description;
    private String createdAt;
}
