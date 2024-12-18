package ru.moscow.tms.tms.controller.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanUpdateDto {
    private Long id;
    private String name;
    private String description;
    private String typeName;
}
