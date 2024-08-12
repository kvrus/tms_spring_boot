package ru.moscow.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseUpdateDto {
    private Long caseId;
    private String name;
    private String description;
}
