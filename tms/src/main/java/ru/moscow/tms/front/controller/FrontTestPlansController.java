package ru.moscow.tms.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.moscow.tms.tms.service.PlanServiceImpl;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import ru.moscow.tms.tms.models.TPlan;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseResponseDto;
import ru.moscow.tms.tms.controller.dto.plan.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:8080")
public class FrontTestPlansController {

    final private PlanServiceImpl service;

    @GetMapping("/plans")
    public String plansPage(Model model) {
        Page<TPlan> pageResult = service.getPlans(0, 20);
        model.addAttribute("testPlans",
                pageResult.get().map(item -> new TestPlanResponseDto(item.getId(), item.getName(), item.getDescription(), item.getPlanType().getName(), TestPlanResponseDto.getUserNameIfExists(item.getAuthor()), item.getCreationDate())).toList()
                );
        return "plans";
    }

    @GetMapping("/cases")
    public String casesPage(Model model) {
        List<TestCaseResponseDto> cases = service.getAllCasesInPlan(2L);
        model.addAttribute("testCases", cases);
        return "cases";
    }

}
