package ru.moscow.tms.tms.controller.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanProcedureDto {
    private Long id;
    private Date creationDate;
}
