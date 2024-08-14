package ru.moscow.tms.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private String name;
    private String description;
    private String plan;
    private String category;
    private String priority;
}
