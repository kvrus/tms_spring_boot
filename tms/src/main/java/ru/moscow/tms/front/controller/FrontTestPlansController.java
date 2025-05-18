package ru.moscow.tms.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ru.moscow.tms.tms.controller.dto.cases.TestCaseDto;
import ru.moscow.tms.tms.service.CaseServiceImpl;
import ru.moscow.tms.tms.service.PlanServiceImpl;
import org.springframework.ui.Model;
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
    final private CaseServiceImpl caseService;

    @GetMapping("/plans")
    public String plansPage(
            @RequestParam(defaultValue = "0") int page, // page starts at 0
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<TPlan> pageResult = service.getPlans(page, size);
        model.addAttribute("testPlans",
                pageResult.get().map(item -> new TestPlanResponseDto(item.getId(), item.getName(), item.getDescription(), item.getPlanType().getName(), TestPlanResponseDto.getUserNameIfExists(item.getAuthor()), item.getCreationDate())).toList()
                );
        model.addAttribute("currentPage",
                pageResult.getNumber()
        );
        model.addAttribute("pageSize",
                pageResult.getSize()
        );
        model.addAttribute("hasNext",
                pageResult.hasNext()
        );
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "plans";
    }

    @GetMapping("/plans/add")
    public String addPlanPage(Model model) {
        TestPlanDto plan = new TestPlanDto();
        model.addAttribute("plan", plan);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        return "add_plan";
    }

    @PostMapping("/plans/add")
    public String savePlan(@ModelAttribute TestPlanDto plan,  @AuthenticationPrincipal UserDetails userDetails) {
        service.createTestPlan(plan, userDetails.getUsername());
        return "redirect:/plans";
    }

    @GetMapping("/cases")
    public String casesPage(Model model) {
        List<TestCaseResponseDto> cases = service.getAllCasesInPlan(2L);
        model.addAttribute("testCases", cases);
        return "cases";
    }

    @GetMapping("/plans/{planId}/cases")
    public String getAllCasesInPlan(@PathVariable("planId") String planId, Model model) {
        List<TestCaseResponseDto> list = service.getAllCasesInPlan(Long.parseLong(planId));
        model.addAttribute("testCases", list);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth",
                auth
        );
        model.addAttribute("testcase", new TestCaseDto());
        return "cases";
    }


    @PostMapping("/plans/{planId}/cases")
    public void handleFormSubmit(@ModelAttribute("testcase") TestCaseDto testcase, @PathVariable("planId") String planId, @AuthenticationPrincipal UserDetails userDetails){
        caseService.createTestCase(testcase, userDetails.getUsername());
    }

}
