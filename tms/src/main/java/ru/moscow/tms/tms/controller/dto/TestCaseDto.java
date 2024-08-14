package ru.moscow.tms.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private Long planId;
    private String name;
    private String description;

}
