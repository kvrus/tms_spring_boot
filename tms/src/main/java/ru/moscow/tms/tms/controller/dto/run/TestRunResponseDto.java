package ru.moscow.tms.tms.controller.dto.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moscow.tms.tms.controller.dto.plan.TestPlanResponseDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRunResponseDto {
    private Long id;
    private String name;
    private String description;
    private TestPlanResponseDto plan;
    private String authorName;
    private Date creation_date;
}
