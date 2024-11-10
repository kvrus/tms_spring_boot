package ru.moscow.tms.tms.controller.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanCalculationsResponseDto {
    private Long planId;
    private String name;
    private Long caseCount;
}
