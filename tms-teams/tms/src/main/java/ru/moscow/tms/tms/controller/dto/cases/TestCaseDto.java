package ru.moscow.tms.tms.controller.dto.cases;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class TestCaseDto {
    @Size(min = 3, max = 256, message = "please set name from 3 to 256 symbols")
    private String name;
    @NotBlank(message = "please add description")
    private String description;
    @NotBlank(message = "please set a plan")
    private String plan;
    @NotBlank(message = "please set a category")
    private String category;
    @NotBlank(message = "please set a priority")
    private String priority;
}


