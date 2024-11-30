package ru.moscow.tms.tms.controller.dto.plan;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanDto {
    @Size(min = 3, max = 256)
    private String name;
    @Size(min = 3, max = 256)
    private String description;
    private String typeName;
}
