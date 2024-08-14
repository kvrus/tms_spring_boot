package ru.moscow.tms.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResponseDto {
    private Long id;
    private String name;
    private String description;
    private String requirements;
    private String plan;
    private String category;
    private String priority;
    private String status;
    private String author;
    private String tester;
    private String reviewer;
}
