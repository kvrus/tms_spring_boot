package ru.moscow.tms.tms.controller.dto.cases;


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
public class TestCaseUpdateDto {
    private Long id;
    @Size(min = 3, max = 256, message = "name is too short")
    private String name;
    @NotBlank(message = "you must pass description")
    private String description;
    @NotBlank(message = "you must pass category")
    private String category;
    @NotBlank(message = "you must pass priority")
    private String priority;
    @NotBlank(message = "you must pass status")
    private String status;
    private Boolean isDeleted;
}
