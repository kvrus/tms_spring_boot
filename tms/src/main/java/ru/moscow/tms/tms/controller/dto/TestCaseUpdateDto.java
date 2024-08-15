package ru.moscow.tms.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseUpdateDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String priority;
    private String status;
}
